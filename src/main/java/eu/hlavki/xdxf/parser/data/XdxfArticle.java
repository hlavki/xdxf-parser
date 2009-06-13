/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hlavki.xdxf.parser.data;

/**
 *
 * @author hlavki
 */
public class XdxfArticle {

    private XdxfFormat format;
    private String key;
    private String translation;

    public XdxfArticle() {
    }

    public XdxfFormat getFormat() {
        return format;
    }

    public void setFormat(XdxfFormat format) {
        this.format = format;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    @Override
    public String toString() {
        return "Article[" + key + " => " + translation + "]";
    }


}
