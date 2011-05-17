/*
 * InvalidElementException.java
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

import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author hlavki
 */
public class InvalidSectionException extends ParseException {

    private static final long serialVersionUID = 340118410658978864L;

    public enum ElementType {

        START, END;

        public String toString(String elem) {
            return "<" + (this.equals(END) ? "/" : "") + elem + ">";
        }
    }

    public InvalidSectionException(ElementType type, String foundEl, String expectedEl, Location location) {
        super("Unknown " + type + " element " + type.toString(foundEl) + " found, but expected " +
                type.toString(expectedEl) + " @ " + location);
    }

    public InvalidSectionException(ElementType type, String foundEl, XDXFElement expectedEl, Location location) {
        super("Unknown " + type + " element " + type.toString(foundEl) + " found, but expected " +
                type.toString(expectedEl.getLocalPart()) + " @ " + location);
    }

    public InvalidSectionException(int eventType, Location location, QName name) {
        super("Unknown section in xdxf format (eventType = " + eventType + 
                (name != null ? " name: " + name : "") + " ) @ location " + location);
    }

    public InvalidSectionException(XMLStreamReader xmlr) {
        this(xmlr.getEventType(), xmlr.getLocation(), xmlr.hasName() ? xmlr.getName() : null);
    }
}
