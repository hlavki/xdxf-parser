/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hlavki.xdxf.parser;

import javax.xml.namespace.QName;

/**
 *
 * @author hlavki
 */
public enum XDXFElement {

    XDXF("xdxf"),
    XDXF_FULL_NAME("full_name"),
    XDXF_DESCRIPTION("description"),
    ARTICLE("ar"),
    ARTICLE_KEY("k");

    private String localPart;

    private XDXFElement(String localPart) {
        this.localPart = localPart;
    }

    public String getLocalPart() {
        return localPart;
    }

    public boolean equals(QName name) {
        return localPart.equals(name.getLocalPart());
    }

    public static XDXFElement fromName(QName name) {
        XDXFElement result = null;
        for (XDXFElement elem : values()) {
            if (elem.getLocalPart().equals(name.getLocalPart())) {
                result = elem;
                break;
            }
        }
        return result;
    }
}
