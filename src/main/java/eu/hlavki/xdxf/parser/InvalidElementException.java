/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hlavki.xdxf.parser;

/**
 *
 * @author hlavki
 */
public class InvalidElementException extends ParseException {

    private static final long serialVersionUID = -2537647436221388835L;

    public enum ElementType {

        START, END
    }

    public InvalidElementException(ElementType type, String foundEl, String expectedEl) {
        super("Unknown " + type + " element " + foundEl + " found but expected " + expectedEl);
    }

    public InvalidElementException(ElementType type, String foundEl, XDXFElement expectedEl) {
        super("Unknown " + type + " element " + foundEl + " found but expected " + expectedEl.toString());
    }
}
