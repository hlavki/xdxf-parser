/*
 * XDXFArticle.java
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
package eu.hlavki.xdxf.parser.data;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Michal HLavac <hlavki@hlavki.eu>
 */
public class XDXFArticle {

    private XDXFFormat format;
    private List<XDXFArticleKeyElement> key;
    private String translation;

    public XDXFArticle() {
        key = new LinkedList<XDXFArticleKeyElement>();
    }

    public XDXFFormat getFormat() {
        return format;
    }

    public void setFormat(XDXFFormat format) {
        this.format = format;
    }

    public List<XDXFArticleKeyElement> getKey() {
        return key;
    }

    public void addKeyElement(XDXFArticleKeyElement keyEl) {
        key.add(keyEl);
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    @Override
    public String toString() {
        return "Article[" + keyAsString() + " => " + translation + "]";
    }

    public String keyAsString() {
        StringBuffer sb = new StringBuffer();
        for (XDXFArticleKeyElement el : key) {
            if (el.optional) {
                sb.append("[");
            }
            sb.append(el.value);
            if (el.optional) {
                sb.append("]");
            }
            sb.append(" ");
        }
        return sb.toString().trim();
    }

    public static class XDXFArticleKeyElement {

        private final String value;
        private final boolean optional;

        public XDXFArticleKeyElement(String value, boolean optional) {
            this.value = value;
            this.optional = optional;
        }

        public boolean isOptional() {
            return optional;
        }

        public String getValue() {
            return value;
        }
    }
}
