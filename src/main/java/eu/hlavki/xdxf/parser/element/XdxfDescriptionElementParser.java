/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hlavki.xdxf.parser.element;

import eu.hlavki.xdxf.parser.InvalidElementException;
import eu.hlavki.xdxf.parser.ParseException;
import static eu.hlavki.xdxf.parser.XdxfElement.*;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author hlavki
 */
public class XdxfDescriptionElementParser implements ElementParser<String> {

    public String parseElement(XMLStreamReader xmlr) throws ParseException {
        String fullName = null;
        try {
            xmlr.next();
            fullName = xmlr.getText();
            xmlr.next();
        } catch (XMLStreamException e) {
            throw new ParseException(e);
        }
        return fullName;
    }
}
