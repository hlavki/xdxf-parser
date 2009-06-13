/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hlavki.xdxf.parser;

import eu.hlavki.xdxf.parser.data.XdxfDictionary;

/**
 *
 * @author hlavki
 */
public class XdxfContext {

    private XdxfDictionary dictionary;

    public XdxfDictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(XdxfDictionary dictionary) {
        this.dictionary = dictionary;
    }
}
