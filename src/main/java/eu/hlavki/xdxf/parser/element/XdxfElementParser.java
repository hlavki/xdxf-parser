/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hlavki.xdxf.parser.element;

import eu.hlavki.xdxf.parser.data.XdxfDictionary;
import static eu.hlavki.xdxf.parser.XdxfElement.*;
import eu.hlavki.xdxf.parser.ParseException;
import eu.hlavki.xdxf.parser.ParserUtil;
import eu.hlavki.xdxf.parser.InvalidElementException;
import eu.hlavki.xdxf.parser.data.XdxfFormat;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author hlavki
 */
public class XdxfElementParser implements ElementParser<XdxfDictionary> {

    private static final String SRC_LANG_ATTR = "lang_from";
    private static final String TARGET_LANG_ATTR = "lang_to";
    private static final String FORMAT_ATTR = "format";

    public XdxfDictionary parseElement(XMLStreamReader xmlr) throws ParseException {
        String srcLanguage = ParserUtil.getAttributeValue(xmlr, SRC_LANG_ATTR);
        if (srcLanguage == null || srcLanguage.length() != 3) {
            throw new ParseException(SRC_LANG_ATTR + " attribute has to be ISO 639.2 code!");
        }
        String targetLanguage = ParserUtil.getAttributeValue(xmlr, TARGET_LANG_ATTR);
        if (targetLanguage == null || targetLanguage.length() != 3) {
            throw new ParseException(TARGET_LANG_ATTR + " attribute has to be ISO 639.2 code!");
        }
        XdxfFormat format = XdxfFormat.fromRealName(ParserUtil.getAttributeValue(xmlr, FORMAT_ATTR));
        if (format == null) {
            throw new ParseException("Format has to be one of values (visual, logical)");
        }
        return new XdxfDictionary(srcLanguage, targetLanguage, format);
    }
}
