/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hlavki.xdxf.parser.element;

import eu.hlavki.xdxf.parser.ParseException;
import eu.hlavki.xdxf.parser.ParserUtil;
import eu.hlavki.xdxf.parser.XDXFElement;
import eu.hlavki.xdxf.parser.data.XDXFArticle;
import eu.hlavki.xdxf.parser.data.XDXFFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author hlavki
 */
public class XDXFArticleElementParser implements ElementParser<XDXFArticle> {

    private static final Logger log = Logger.getLogger(XDXFArticleElementParser.class.getName());
    private static final String FORMAT_ATTR = "f";

    public XDXFArticle parseElement(XMLStreamReader xmlr) throws ParseException {
        String formatStr = ParserUtil.getAttributeValue(xmlr, FORMAT_ATTR);
        XDXFArticle result = new XDXFArticle();
        if (formatStr != null) {
            result.setFormat(XDXFFormat.fromRealName(formatStr));
        }
        try {
            xmlr.next();
            ParserUtil.checkStartElement(xmlr, XDXFElement.ARTICLE_KEY);
            xmlr.next();
            result.setKey(ParserUtil.readString(xmlr).trim());
            ParserUtil.checkEndElement(xmlr, XDXFElement.ARTICLE_KEY);
            xmlr.next();
            result.setTranslation(ParserUtil.readString(xmlr).trim());
        } catch (XMLStreamException e) {
            log.severe(result.toString());
            throw new ParseException(e);
        } catch (ParseException e) {
            log.severe(result.toString());
            throw e;
        }
        if (log.isLoggable(Level.FINE)) {
            log.fine(result.toString());
        }
        return result;
    }
}
