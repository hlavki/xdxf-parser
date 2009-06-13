/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hlavki.xdxf.parser.events;

import eu.hlavki.xdxf.parser.data.XdxfDictionary;
import java.util.EventObject;

/**
 *
 * @author hlavki
 */
public class XdxfDictionaryEvent extends EventObject {

    private static final long serialVersionUID = -5997566225611192690L;

    public XdxfDictionaryEvent(XdxfDictionary src) {
        super(src);
    }

    @Override
    public XdxfDictionary getSource() {
        return (XdxfDictionary) super.getSource();
    }
}
