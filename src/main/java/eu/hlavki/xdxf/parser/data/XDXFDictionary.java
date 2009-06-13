/*
 * XDXFDictionary.java
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

/**
 * 
 * @author Michal HLavac <hlavki@hlavki.eu>
 */
public class XDXFDictionary {

    private final String srcLanguage;
    private final String targetLanguage;
    private final XDXFFormat format;
    private String fullName;
    private String description;

    public XDXFDictionary(String srcLanguage, String targetanguage, XDXFFormat format) {
        this.srcLanguage = srcLanguage;
        this.targetLanguage = targetanguage;
        this.format = format;
    }

    public String getSrcLanguage() {
        return srcLanguage;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public XDXFFormat getFormat() {
        return format;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("==============================================\n");
        sb.append("Source language: ").append(srcLanguage).append("\n");
        sb.append("Target language: ").append(targetLanguage).append("\n");
        sb.append("Full Name: ").append(targetLanguage != null ? targetLanguage : "").append("\n");
        sb.append("Description: ").append(description != null ? description : "").append("\n");
        sb.append("==============================================");
        return sb.toString();
    }
}
