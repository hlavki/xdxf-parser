/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hlavki.xdxf.parser.data;

/**
 *
 * @author hlavki
 */
public class Dictionary {

    private final String srcLanguage;
    private final String targetanguage;
    private final XdxfFormat format;
    private String fullName;
    private String description;

    public Dictionary(String srcLanguage, String targetanguage, XdxfFormat format) {
        this.srcLanguage = srcLanguage;
        this.targetanguage = targetanguage;
        this.format = format;
    }

    public String getSrcLanguage() {
        return srcLanguage;
    }

    public String getTargetanguage() {
        return targetanguage;
    }

    public XdxfFormat getFormat() {
        return format;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
