/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hlavki.xdxf.parser.data;

/**
 *
 * @author hlavki
 */
public class XdxfDictionary {

    private final String srcLanguage;
    private final String targetLanguage;
    private final XdxfFormat format;
    private String fullName;
    private String description;

    public XdxfDictionary(String srcLanguage, String targetanguage, XdxfFormat format) {
        this.srcLanguage = srcLanguage;
        this.targetLanguage = targetanguage;
        this.format = format;
    }

    public String getSrcLanguage() {
        return srcLanguage;
    }

    public String getTargetLanguage() {
        return targetLanguage;
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

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("==============================================\n");
        sb.append("Source language: ").append(srcLanguage).append("\n");
        sb.append("Target language: ").append(targetLanguage).append("\n");
        sb.append("Full Name: ").append(targetLanguage != null ? targetLanguage : "").append("\n");
        sb.append("Description: ").append(description != null ? description : "").append("\n");
        sb.append("==============================================");
        return sb.toString();
    }
}
