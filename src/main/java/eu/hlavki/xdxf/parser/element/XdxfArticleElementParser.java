/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hlavki.xdxf.parser.element;

import eu.hlavki.xdxf.parser.ParseException;
import eu.hlavki.xdxf.parser.ParserUtil;
import eu.hlavki.xdxf.parser.XdxfElement;
import eu.hlavki.xdxf.parser.data.XdxfArticle;
import eu.hlavki.xdxf.parser.data.Dictionary;
import eu.hlavki.xdxf.parser.data.XdxfFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author hlavki
 */
public class XdxfArticleElementParser implements ElementParser<XdxfArticle> {

    private static final Logger log = Logger.getLogger(XdxfArticleElementParser.class.getName());
    private static final String FORMAT_ATTR = "f";

    public XdxfArticle parseElement(XMLStreamReader xmlr) throws ParseException {
        String formatStr = ParserUtil.getAttributeValue(xmlr, FORMAT_ATTR);
        XdxfArticle result = new XdxfArticle();
        if (formatStr != null) {
            result.setFormat(XdxfFormat.fromRealName(formatStr));
        }
        try {
            xmlr.next();
            ParserUtil.checkStartElement(xmlr, XdxfElement.ARTICLE_KEY);
            xmlr.next();
            result.setKey(ParserUtil.readString(xmlr).trim());
            ParserUtil.checkEndElement(xmlr, XdxfElement.ARTICLE_KEY);
            xmlr.next();
            result.setTranslation(ParserUtil.readString(xmlr).trim());
        } catch (XMLStreamException e) {
            log.severe(result.toString());
            throw new ParseException(e);
        } catch (ParseException e) {
            log.severe(result.toString());
            throw e;
        }
        log.fine(result.toString());
        return result;
    }
}
