/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hlavki.xdxf.parser.element;

import eu.hlavki.xdxf.parser.ParseException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @param <T> 
 * @author hlavki
 */
public interface ElementParser<T> {

    /**
     * 
     * @param xmlr
     * @return
     * @throws ParseException
     */
    T parseElement(XMLStreamReader xmlr) throws ParseException;
}
