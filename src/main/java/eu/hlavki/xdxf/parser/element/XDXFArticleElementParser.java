/*
 * XDXFArticleElementParser.java
 *
 * Copyright (c) 2009 Michal HLavac <hlavki@hlavki.eu>. All rights reserved.
 *
 * This file is part of XDXF Parser (jar).
 *
 * XDXF Parser (jar) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * XDXF Parser (jar) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with XDXF Parser (jar).  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.hlavki.xdxf.parser.element;

import eu.hlavki.xdxf.parser.InvalidSectionException;
import eu.hlavki.xdxf.parser.ParseException;
import eu.hlavki.xdxf.parser.ParserUtil;
import eu.hlavki.xdxf.parser.XDXFElement;
import static eu.hlavki.xdxf.parser.XDXFElement.*;
import eu.hlavki.xdxf.parser.model.XDXFArticle;
import eu.hlavki.xdxf.parser.model.XDXFArticle.XDXFArticleKey;
import eu.hlavki.xdxf.parser.model.XDXFArticle.XDXFArticleKeyItem;
import eu.hlavki.xdxf.parser.model.XDXFArticle.XDXFArticleKeyItemType;
import eu.hlavki.xdxf.parser.model.XDXFFormat;
import java.util.EnumSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Michal HLavac
 */
public class XDXFArticleElementParser implements ElementParser<XDXFArticle> {

    private static final Logger log = Logger.getLogger(XDXFArticleElementParser.class.getName());
    private static final String FORMAT_ATTR = "f";
    private static final Set<XDXFElement> ARTICLE_PARENT_ELEMS = EnumSet.of(ARTICLE, ARTICLE_KEY);

    public XDXFArticle parseElement(XMLStreamReader xmlr) throws ParseException {
        String formatStr = ParserUtil.getAttributeValue(xmlr, FORMAT_ATTR);
        XDXFArticle result = new XDXFArticle();
        if (formatStr != null) {
            result.setFormat(XDXFFormat.fromRealName(formatStr));
        }
        try {
            boolean readNext = true;
            while (!ParserUtil.checkFor(XMLEvent.END_ELEMENT, xmlr, ARTICLE)) {
                if (readNext) {
                    xmlr.next();
                } else {
                    readNext = true;
                }
                int eventType = xmlr.getEventType();
                if (eventType == XMLEvent.START_ELEMENT) {
                    XDXFElement el = XDXFElement.fromName(xmlr, ARTICLE_PARENT_ELEMS, false);
                    switch (el) {
                        case ARTICLE:
                            // do nothing
                            break;
                        case ARTICLE_KEY:
                            xmlr.next();
                            XDXFArticleKey key = new XDXFArticleKey();
                            while (!ParserUtil.checkFor(XMLEvent.END_ELEMENT, xmlr, ARTICLE_KEY)) {
                                if (ParserUtil.checkFor(XMLEvent.START_ELEMENT, xmlr, ARTICLE_KEY_OPT)) {
                                    xmlr.next(); // if <opt> element, move to characters
                                    String item = ParserUtil.readString(xmlr).trim();
                                    key.addItem(new XDXFArticleKeyItem(item, XDXFArticleKeyItemType.OPTIONAL));
                                    ParserUtil.assertEndElement(xmlr, ARTICLE_KEY_OPT);
                                    xmlr.next(); // move one step after </opt>
                                } else if (ParserUtil.checkFor(XMLEvent.START_ELEMENT, xmlr, ARTICLE_KEY_NU)) {
                                    xmlr.next(); // if <opt> element, move to characters
                                    String item = ParserUtil.readString(xmlr).trim();
                                    key.addItem(new XDXFArticleKeyItem(item, XDXFArticleKeyItemType.NOT_USED));
                                    ParserUtil.assertEndElement(xmlr, ARTICLE_KEY_NU);
                                    xmlr.next(); // move one step after </opt>
                                } else if (xmlr.getEventType() == XMLEvent.CHARACTERS) {
                                    String item = ParserUtil.readString(xmlr).trim();
                                    key.addItem(new XDXFArticleKeyItem(item));
                                } else {
                                    throw new InvalidSectionException(xmlr);
                                }
                            }
                            result.addKey(key);
                            ParserUtil.assertEndElement(xmlr, ARTICLE_KEY);
                            break;
                        case ARTICLE_POS:
                            result.setPartOfSpeech(new XDXFArticlePosElementParser().parseElement(xmlr));
                            ParserUtil.assertEndElement(xmlr, ARTICLE_POS);
                            break;
                        case ARTICLE_TENSE:
                            result.setTense(xmlr.getElementText().trim());
                            ParserUtil.assertEndElement(xmlr, ARTICLE_TENSE);
                            break;
                        case ARTICLE_TR:
                            result.setTranscription(xmlr.getElementText().trim());
                            ParserUtil.assertEndElement(xmlr, ARTICLE_TR);
                            break;
                        default:
                            throw new InvalidSectionException(xmlr);

                    }
                } else if (eventType == XMLEvent.END_ELEMENT) {
                    XDXFElement el = XDXFElement.fromName(xmlr, ARTICLE_PARENT_ELEMS, false);
                    switch (el) {
                        case ARTICLE:
                            // do nothing
                            break;
                    }
                } else if (eventType == XMLEvent.CHARACTERS) {
                    String val = ParserUtil.readString(xmlr).trim();
                    readNext = false;
                    if (ParserUtil.checkFor(XMLEvent.END_ELEMENT, xmlr, ARTICLE)) {
                        result.setTranslation(val);
                    }
                } else {
                    throw new InvalidSectionException(xmlr);
                }
            }
        } catch (XMLStreamException e) {
            log.severe(result.toString());
            throw new ParseException(e);
        } catch (ParseException e) {
            log.severe(result.toString());
            throw e;
        }
        if (log.isLoggable(Level.FINE)) {
            log.fine(result.toString());
        }
        return result;
    }
}
