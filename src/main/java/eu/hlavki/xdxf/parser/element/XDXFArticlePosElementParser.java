/*
 * XDXAbbreviationElementParser.java
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
import eu.hlavki.xdxf.parser.model.XDXFArticle;
import java.util.EnumSet;
import java.util.Set;
import static eu.hlavki.xdxf.parser.XDXFElement.*;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Michal HLavac
 */
public class XDXFArticlePosElementParser implements ElementParser<XDXFArticle.XDXFArticlePosItem> {

    private static final Logger log = Logger.getLogger(XDXFArticlePosElementParser.class.getName());
    private static final Set<XDXFElement> PARENTS = EnumSet.of(ARTICLE_POS);

    public XDXFArticle.XDXFArticlePosItem parseElement(XMLStreamReader xmlr) throws ParseException {
        boolean abbr = false;
        XDXFArticle.XDXFArticlePosItem result = null;
        try {
            while (!ParserUtil.checkFor(XMLEvent.END_ELEMENT, xmlr, ARTICLE_POS)) {
                xmlr.next(); // next fragment
                int eventType = xmlr.getEventType();
                if (eventType == XMLEvent.START_ELEMENT) {
                    XDXFElement el = XDXFElement.fromName(xmlr, PARENTS, false);
                    switch (el) {
                        case ARTICLE_POS_ABBR:
                            abbr = true;
                            break;
                        default:
                            throw new InvalidSectionException(xmlr);
                    }
                } else if (eventType == XMLEvent.END_ELEMENT) {
                    XDXFElement el = XDXFElement.fromName(xmlr, EnumSet.of(ARTICLE), false);
                    switch (el) {
                        case ARTICLE_POS:
                            // do nothing
                            break;
                    }
                } else if (eventType == XMLEvent.CHARACTERS) {
                    String val = ParserUtil.readString(xmlr).trim();
                    result = new XDXFArticle.XDXFArticlePosItem(val, abbr);
                } else {
                    throw new InvalidSectionException(xmlr);
                }
            }
        } catch (XMLStreamException e) {
            throw new ParseException(e);
        } catch (ParseException e) {
            throw e;
        }
        return result;
    }
}
