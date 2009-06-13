/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hlavki.xdxf.parser;

import eu.hlavki.xdxf.parser.data.XDXFDictionary;

/**
 *
 * @author hlavki
 */
public class XDXFContext {

    private XDXFDictionary dictionary;

    public XDXFDictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(XDXFDictionary dictionary) {
        this.dictionary = dictionary;
    }
}
