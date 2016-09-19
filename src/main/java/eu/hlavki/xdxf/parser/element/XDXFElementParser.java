/*
 * XDXFElementParser.java
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

import eu.hlavki.xdxf.parser.model.XDXFDictionary;
import eu.hlavki.xdxf.parser.ParseException;
import eu.hlavki.xdxf.parser.ParserUtil;
import eu.hlavki.xdxf.parser.model.XDXFFormat;
import java.util.EnumSet;
import java.util.Set;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author Michal HLavac
 */
public class XDXFElementParser implements ElementParser<XDXFDictionary> {

    private static final String SRC_LANG_ATTR = "lang_from";
    private static final String TARGET_LANG_ATTR = "lang_to";
    private static final String FORMAT_ATTR = "format";
    private static final Set<XDXFFormat> VALID_FORMATS = EnumSet.of(XDXFFormat.VISUAL);

    public XDXFDictionary parseElement(XMLStreamReader xmlr) throws ParseException {
        String srcLanguage = ParserUtil.getAttributeValue(xmlr, SRC_LANG_ATTR);
        if (srcLanguage == null || srcLanguage.length() != 3) {
            throw new ParseException(SRC_LANG_ATTR + " attribute has to be ISO 639.2 code!");
        }
        String targetLanguage = ParserUtil.getAttributeValue(xmlr, TARGET_LANG_ATTR);
        if (targetLanguage == null || targetLanguage.length() != 3) {
            throw new ParseException(TARGET_LANG_ATTR + " attribute has to be ISO 639.2 code!");
        }
        XDXFFormat format = XDXFFormat.fromRealName(ParserUtil.getAttributeValue(xmlr, FORMAT_ATTR));
        if (format == null || !VALID_FORMATS.contains(format)) {
            throw new ParseException("Format has to be one of values " + VALID_FORMATS);
        }
        return new XDXFDictionary(srcLanguage, targetLanguage, format);
    }
}
