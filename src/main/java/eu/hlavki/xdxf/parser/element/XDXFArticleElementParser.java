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

import eu.hlavki.xdxf.parser.ParseException;
import eu.hlavki.xdxf.parser.ParserUtil;
import static eu.hlavki.xdxf.parser.XDXFElement.*;
import eu.hlavki.xdxf.parser.data.XDXFArticle;
import eu.hlavki.xdxf.parser.data.XDXFFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Michal HLavac <hlavki@hlavki.eu>
 */
public class XDXFArticleElementParser implements ElementParser<XDXFArticle> {

    private static final Logger log = Logger.getLogger(XDXFArticleElementParser.class.getName());
    private static final String FORMAT_ATTR = "f";

    public XDXFArticle parseElement(XMLStreamReader xmlr) throws ParseException {
        String formatStr = ParserUtil.getAttributeValue(xmlr, FORMAT_ATTR);
        XDXFArticle result = new XDXFArticle();
        if (formatStr != null) {
            result.setFormat(XDXFFormat.fromRealName(formatStr));
        }
        try {
            ParserUtil.gotoNextElement(XMLEvent.START_ELEMENT, xmlr, ARTICLE_KEY);
            ParserUtil.checkStartElement(xmlr, ARTICLE_KEY);
            xmlr.next(); // move to next fragment (<opt> element or characters)
            while (!ParserUtil.checkFor(XMLEvent.END_ELEMENT, xmlr, ARTICLE_KEY)) {
                if (ParserUtil.checkFor(XMLEvent.START_ELEMENT, xmlr, ARTICLE_KEY_OPT)) {
                    xmlr.next(); // if <opt> element, move to characters
                    result.addKeyElement(new XDXFArticle.XDXFArticleKeyElement(ParserUtil.readString(xmlr).trim(), true));
                    ParserUtil.checkEndElement(xmlr, ARTICLE_KEY_OPT);
                    xmlr.next(); // move one step after </opt>
                } else if (xmlr.getEventType() == XMLEvent.CHARACTERS) {
                    result.addKeyElement(new XDXFArticle.XDXFArticleKeyElement(ParserUtil.readString(xmlr).trim(), false));
                } else {
                    throw new ParseException("Uknown xdxf format! Unexpected behaviour at " + xmlr.getLocation());
                }
            }
            ParserUtil.checkEndElement(xmlr, ARTICLE_KEY);
            xmlr.next();
            result.setTranslation(ParserUtil.readString(xmlr).trim());
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
