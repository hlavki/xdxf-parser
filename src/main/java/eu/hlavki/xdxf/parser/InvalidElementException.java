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

/**
 *
 * @author hlavki
 */
public class InvalidElementException extends ParseException {

    private static final long serialVersionUID = -2537647436221388835L;

    public enum ElementType {

        START, END
    }

    public InvalidElementException(ElementType type, String foundEl, String expectedEl) {
        super("Unknown " + type + " element " + foundEl + " found but expected " + expectedEl);
    }

    public InvalidElementException(ElementType type, String foundEl, XDXFElement expectedEl) {
        super("Unknown " + type + " element " + foundEl + " found but expected " + expectedEl.toString());
    }
}
