/*
 * XDXFFormat.java
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
public enum XDXFFormat {

    LOGICAL("logical"),
    VISUAL("visual");
    String realName;

    private XDXFFormat(String realName) {
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }

    public static XDXFFormat fromRealName(String realName) {
        XDXFFormat result = null;
        for (XDXFFormat format : values()) {
            if (realName != null && realName.equals(format.getRealName())) {
                result = format;
                break;
            }
        }
        return result;
    }
}
