/*
 * XDXFAbbreviationsElementParser.java
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
import eu.hlavki.xdxf.parser.InvalidSectionException;
import eu.hlavki.xdxf.parser.XDXFElement;
import static eu.hlavki.xdxf.parser.XDXFElement.*;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Michal HLavac <hlavki@hlavki.eu>
 */
public class XDXFAbbreviationsElementParser implements ElementParser<Map<String, String>> {

    private static final Set<XDXFElement> ABBR_PARENT_ELEMS = EnumSet.of(ABBREVIATIONS, ABBREVIATION_DEF, XDXF);

    public Map<String, String> parseElement(XMLStreamReader xmlr) throws ParseException {
        Map<String, String> result = new HashMap<String, String>();
        try {
//            xmlr.next();
//            ParserUtil.gotoNextElement(XMLEvent.START_ELEMENT, xmlr); // move to first <abr> element
//            ParserUtil.checkStartElement(xmlr, ABBREVIATION_DEF);
            Set<String> abbrKeys = new HashSet<String>();
            String abbrValue = null;
            while (!ParserUtil.checkFor(XMLEvent.END_ELEMENT, xmlr, ABBREVIATIONS)) {
                xmlr.next(); // next fragment
                int eventType = xmlr.getEventType();
                if (eventType == XMLEvent.START_ELEMENT) {
                    XDXFElement el = XDXFElement.fromName(xmlr, ABBR_PARENT_ELEMS, false);
                    switch (el) {
                        case ABBREVIATION_DEF:
                            // do nothing
                            break;
                        case ABBREVIATION_DEF_KEY:
                            abbrKeys.add(xmlr.getElementText().trim());
                            ParserUtil.assertEndElement(xmlr, ABBREVIATION_DEF_KEY);
                            break;
                        case ABBREVIATION_DEF_VAL:
                            abbrValue = (xmlr.getElementText().trim());
                            ParserUtil.assertEndElement(xmlr, ABBREVIATION_DEF_VAL);
                            break;
                        default:
                            throw new InvalidSectionException(xmlr);

                    }
                } else if (eventType == XMLEvent.END_ELEMENT) {
                    XDXFElement el = XDXFElement.fromName(xmlr, ABBR_PARENT_ELEMS, false);
                    switch (el) {
                        case ABBREVIATION_DEF:
                            if (abbrValue != null) {
                                for (String key : abbrKeys) {
                                    result.put(key, abbrValue);
                                }
                            }
                            abbrKeys.clear();
                            abbrValue = null;
                            break;
                        case ABBREVIATIONS:
                            // do nothing
                            break;
                    }
                } else if (eventType != XMLEvent.CHARACTERS) {
                    throw new InvalidSectionException(xmlr);
                }
            }
            ParserUtil.assertEndElement(xmlr, ABBREVIATIONS);
        } catch (XMLStreamException e) {
            throw new ParseException(e);
        }
        return result;
    }
}
