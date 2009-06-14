/*
 * XDXFElement.java
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

/**
 * 
 * @author Michal HLavac <hlavki@hlavki.eu>
 */
public enum XDXFElement {

    XDXF("xdxf"),
    XDXF_FULL_NAME("full_name"),
    XDXF_DESCRIPTION("description"),
    ARTICLE("ar"),
    ARTICLE_KEY("k"),
    ABBREVIATIONS("abbreviations"),
    ARTICLE_KEY_OPT("opt");

    private String localPart;

    private XDXFElement(String localPart) {
        this.localPart = localPart;
    }

    public String getLocalPart() {
        return localPart;
    }

    public boolean equals(QName name) {
        return localPart.equals(name.getLocalPart());
    }

    public static XDXFElement fromName(QName name) {
        XDXFElement result = null;
        for (XDXFElement elem : values()) {
            if (elem.getLocalPart().equals(name.getLocalPart())) {
                result = elem;
                break;
            }
        }
        return result;
    }
}
