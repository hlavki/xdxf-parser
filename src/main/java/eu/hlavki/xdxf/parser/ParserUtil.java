/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hlavki.xdxf.parser;

import eu.hlavki.xdxf.parser.InvalidElementException.ElementType;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author hlavki
 */
public class ParserUtil {

    public static String getAttributeValue(XMLStreamReader xmlr, String name) {
        int attrCount = xmlr.getAttributeCount();
        String result = null;
        int idx = 0;
        while (idx < attrCount && result == null) {
            if (xmlr.getAttributeName(idx).equals(new QName(name))) {
                result = xmlr.getAttributeValue(idx);
            }
            idx++;
        }
        return result;
    }

    /**
     * check for event type which contains name
     * @throws ParseException
     */
    public static boolean checkFor(int eventType, XMLStreamReader xmlr, XDXFElement el) throws ParseException {
        return (xmlr.getEventType() == eventType) && xmlr.hasName() && el.equals(xmlr.getName());
    }


    /**
     * check for start element
     * @throws ParseException
     */
    public static void checkStartElement(XMLStreamReader xmlr, XDXFElement el) throws ParseException {
        if (!xmlr.isStartElement() || !xmlr.hasName() || !el.equals(xmlr.getName())) {
            String found = (xmlr.hasName() ? xmlr.getName().getLocalPart() : "unkown");
            throw new InvalidElementException(ElementType.START, found, el);
        }
    }

    /**
     * check for end element
     * @throws ParseException
     */
    public static void checkEndElement(XMLStreamReader xmlr, XDXFElement el) throws ParseException {
        if (!xmlr.isEndElement() || !xmlr.hasName() || !el.equals(xmlr.getName())) {
            String found = (xmlr.hasName() ? xmlr.getName().getLocalPart() : "unkown[" + xmlr.getEventType() + "]");
            throw new InvalidElementException(ElementType.END, found, el);
        }
    }

    public static String readString(XMLStreamReader xmlr) throws XMLStreamException {
        StringBuffer sb = new StringBuffer();
        while (xmlr.getEventType() == XMLStreamConstants.CHARACTERS) {
            sb.append(xmlr.getText());
            xmlr.next();
        }
        return sb.toString();
    }
}
