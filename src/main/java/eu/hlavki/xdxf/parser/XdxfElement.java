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
public enum XdxfElement {

    XDXF("xdxf"),
    XDXF_FULL_NAME("full_name"),
    XDXF_DESCRIPTION("description"),
    ARTICLE("ar"),
    ARTICLE_KEY("k");

    private String localPart;

    private XdxfElement(String localPart) {
        this.localPart = localPart;
    }

    public String getLocalPart() {
        return localPart;
    }

    public boolean equals(QName name) {
        return localPart.equals(name.getLocalPart());
    }

    public static XdxfElement fromName(QName name) {
        XdxfElement result = null;
        for (XdxfElement elem : values()) {
            if (elem.getLocalPart().equals(name.getLocalPart())) {
                result = elem;
                break;
            }
        }
        return result;
    }
}
