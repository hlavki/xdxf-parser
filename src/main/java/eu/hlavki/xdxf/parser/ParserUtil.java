/*
 * ParserUtil.java
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
package eu.hlavki.xdxf.parser;

import eu.hlavki.xdxf.parser.InvalidElementException.ElementType;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * 
 * @author Michal HLavac <hlavki@hlavki.eu>
 */
public class ParserUtil {

    public static String getAttributeValue(XMLStreamReader xmlr, String name) {
        int attrCount = xmlr.getAttributeCount();
        String result = null;
        int idx = 0;
        while (idx < attrCount && result == null) {
            if (xmlr.getAttributeName(idx).equals(new QName(name))) {
                result = xmlr.getAttributeValue(idx);
            }
            idx++;
        }
        return result;
    }

    /**
     * check for event type which contains name
     * @throws ParseException
     */
    public static boolean checkFor(int eventType, XMLStreamReader xmlr, XDXFElement el) throws ParseException {
        return (xmlr.getEventType() == eventType) && xmlr.hasName() && el.equals(xmlr.getName());
    }


    /**
     * check for start element
     * @throws ParseException
     */
    public static void checkStartElement(XMLStreamReader xmlr, XDXFElement el) throws ParseException {
        if (!xmlr.isStartElement() || !xmlr.hasName() || !el.equals(xmlr.getName())) {
            String found = (xmlr.hasName() ? xmlr.getName().getLocalPart() : "unkown");
            throw new InvalidElementException(ElementType.START, found, el);
        }
    }

    /**
     * check for end element
     * @throws ParseException
     */
    public static void checkEndElement(XMLStreamReader xmlr, XDXFElement el) throws ParseException {
        if (!xmlr.isEndElement() || !xmlr.hasName() || !el.equals(xmlr.getName())) {
            String found = (xmlr.hasName() ? xmlr.getName().getLocalPart() : "unkown[" + xmlr.getEventType() + "]");
            throw new InvalidElementException(ElementType.END, found, el);
        }
    }

    public static String readString(XMLStreamReader xmlr) throws XMLStreamException {
        StringBuffer sb = new StringBuffer();
        while (xmlr.getEventType() == XMLStreamConstants.CHARACTERS) {
            sb.append(xmlr.getText());
            xmlr.next();
        }
        return sb.toString();
    }
}
