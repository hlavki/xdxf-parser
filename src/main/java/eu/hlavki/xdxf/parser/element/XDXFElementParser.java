/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hlavki.xdxf.parser.element;

import eu.hlavki.xdxf.parser.data.XDXFDictionary;
import static eu.hlavki.xdxf.parser.XDXFElement.*;
import eu.hlavki.xdxf.parser.ParseException;
import eu.hlavki.xdxf.parser.ParserUtil;
import eu.hlavki.xdxf.parser.InvalidElementException;
import eu.hlavki.xdxf.parser.data.XDXFFormat;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author hlavki
 */
public class XDXFElementParser implements ElementParser<XDXFDictionary> {

    private static final String SRC_LANG_ATTR = "lang_from";
    private static final String TARGET_LANG_ATTR = "lang_to";
    private static final String FORMAT_ATTR = "format";

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
        if (format == null) {
            throw new ParseException("Format has to be one of values (visual, logical)");
        }
        return new XDXFDictionary(srcLanguage, targetLanguage, format);
    }
}
