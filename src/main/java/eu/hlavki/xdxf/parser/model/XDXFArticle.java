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
package eu.hlavki.xdxf.parser.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Michal Hlavac
 */
public class XDXFArticle {

    private XDXFFormat format;
    private List<XDXFArticleKey> keys;
    private String translation;
    private XDXFArticlePosItem partOfSpeech;
    private String tense;
    private String transcription;

    public XDXFArticle() {
        keys = new ArrayList<XDXFArticleKey>();
    }

    public XDXFFormat getFormat() {
        return format;
    }

    public void setFormat(XDXFFormat format) {
        this.format = format;
    }

    public List<XDXFArticleKey> getKeys() {
        return keys;
    }

    public void addKey(XDXFArticleKey key) {
        keys.add(key);
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public XDXFArticlePosItem getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(XDXFArticlePosItem partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String getTense() {
        return tense;
    }

    public void setTense(String tense) {
        this.tense = tense;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    @Override
    public String toString() {
        return "Article[" + keysToString() + " => " + translation + "]";
    }

    protected String keysToString() {
        StringBuffer sb = new StringBuffer();
        int size = keys.size(), idx = 0;
        for (XDXFArticleKey key : keys) {
            sb.append(key.toString());
            if (idx < size - 1) {
                sb.append('|');
            }
            idx++;
        }
        return sb.toString();
    }

    public static class XDXFArticleKey {

        List<XDXFArticleKeyItem> items;

        public XDXFArticleKey() {
            items = new LinkedList<XDXFArticleKeyItem>();
        }

        public void addItem(XDXFArticleKeyItem item) {
            items.add(item);
        }

        public String toString(boolean optional) {
            return toString(optional, ' ');
        }

        public String toString(boolean optional, char optMark) {
            return toString(optional, optMark, optMark);
        }

        public String toString(boolean optional, char startOptMark, char endOptMark) {
            StringBuffer sb = new StringBuffer();
            for (XDXFArticleKeyItem el : items) {
                if (!el.isOptional() || (el.isOptional() && optional)) {
                    if (el.isOptional()) {
                        sb.append(startOptMark);
                    }
                    sb.append(el.value);
                    if (el.isOptional()) {
                        sb.append(endOptMark);
                    }
                    sb.append(" ");
                }
            }
            return sb.toString().trim();
        }

        @Override
        public String toString() {
            return toString(true, '(', ')');
        }
    }

    public enum XDXFArticleKeyItemType {

        NORMAL, OPTIONAL, NOT_USED;
    }

    public static class XDXFArticleKeyItem {

        private final String value;
        private final XDXFArticleKeyItemType type;

        public XDXFArticleKeyItem(String value) {
            this(value, XDXFArticleKeyItemType.NORMAL);
        }

        public XDXFArticleKeyItem(String value, XDXFArticleKeyItemType type) {
            this.value = value;
            this.type = type;
        }

        public XDXFArticleKeyItemType getType() {
            return type;
        }

        public String getValue() {
            return value;
        }

        public boolean isOptional() {
            return type == XDXFArticleKeyItemType.OPTIONAL;
        }

        public boolean isNotUsed() {
            return type == XDXFArticleKeyItemType.NOT_USED;
        }
    }

    public static class XDXFArticlePosItem {

        private final String value;
        private final boolean abbreviation;

        public XDXFArticlePosItem(String value, boolean abbreviation) {
            this.value = value;
            this.abbreviation = abbreviation;
        }

        public boolean isAbbreviation() {
            return abbreviation;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "{" + value + " | abbr: " + abbreviation + "}";
        }
    }
}
