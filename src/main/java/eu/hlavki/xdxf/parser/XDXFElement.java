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

import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;

/**
 * 
 * @author Michal HLavac <hlavki@hlavki.eu>
 */
public enum XDXFElement {

    XDXF("xdxf"),
    XDXF_FULL_NAME("full_name", XDXF),
    XDXF_DESCRIPTION("description", XDXF),
    ARTICLE("ar", XDXF),
    ARTICLE_KEY("k", ARTICLE),
    ABBREVIATIONS("abbreviations", XDXF),
    ARTICLE_KEY_OPT("opt", ARTICLE_KEY),
    ABBREVIATION_DEF("abr_def", ABBREVIATIONS),
    ABBREVIATION_DEF_KEY("k", ABBREVIATION_DEF),
    ABBREVIATION_DEF_VAL("v", ABBREVIATION_DEF),
    ARTICLE_POS("pos", ARTICLE),
    ARTICLE_TENSE("tense", ARTICLE),
    ARTICLE_POS_ABBR("abr", ARTICLE_POS),
    ARTICLE_TR("tr", ARTICLE);

    private String localPart;
    private XDXFElement parent;

    private XDXFElement(String localPart) {
        this(localPart, null);
    }

    private XDXFElement(String localPart, XDXFElement parent) {
        this.localPart = localPart;
        this.parent = parent;
    }

    public String getLocalPart() {
        return localPart;
    }

    public boolean equals(QName name) {
        return localPart.equals(name.getLocalPart());
    }

    public XDXFElement getParent() {
        return parent;
    }

    public int getDepth() {
        int depth = 0;
        XDXFElement el = this;
        while (el.parent != null) {
            depth++;
            el = el.parent;
        }
        return depth;
    }

    public static XDXFElement fromName(XMLStreamReader xmlr, Set<XDXFElement> parents, boolean acceptRoot) throws ParseException {
        XDXFElement result = null;
        for (XDXFElement elem : values()) {
            boolean correctParent = elem.parent != null ? parents.contains(elem.parent) : acceptRoot;
            if (correctParent && elem.getLocalPart().equals(xmlr.getLocalName())) {
                result = elem;
                break;
            }
        }
        if (result == null) {
            throw new InvalidSectionException(xmlr);
        }
        return result;
    }
}
