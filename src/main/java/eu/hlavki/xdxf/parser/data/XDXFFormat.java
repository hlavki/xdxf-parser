/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hlavki.xdxf.parser.data;

/**
 *
 * @author hlavki
 */
public enum XDXFFormat {

    LOGICAL("logical"),
    VISUAL("visual");
    String realName;

    private XDXFFormat(String realName) {
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }

    public static XDXFFormat fromRealName(String realName) {
        XDXFFormat result = null;
        for (XDXFFormat format : values()) {
            if (realName != null && realName.equals(format.getRealName())) {
                result = format;
                break;
            }
        }
        return result;
    }
}
