/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hlavki.xdxf.parser.event;

import eu.hlavki.xdxf.parser.data.XDXFDictionary;
import java.util.EventObject;

/**
 *
 * @author hlavki
 */
public class XDXFDictionaryEvent extends EventObject {

    private static final long serialVersionUID = -5997566225611192690L;

    public XDXFDictionaryEvent(XDXFDictionary src) {
        super(src);
    }

    @Override
    public XDXFDictionary getSource() {
        return (XDXFDictionary) super.getSource();
    }
}
