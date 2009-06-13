/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hlavki.xdxf.parser.data;

/**
 *
 * @author hlavki
 */
public enum XdxfFormat {

    LOGICAL("logical"),
    VISUAL("visual");
    String realName;

    private XdxfFormat(String realName) {
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }

    public static XdxfFormat fromRealName(String realName) {
        XdxfFormat result = null;
        for (XdxfFormat format : values()) {
            if (realName != null && realName.equals(format.getRealName())) {
                result = format;
                break;
            }
        }
        return result;
    }
}
