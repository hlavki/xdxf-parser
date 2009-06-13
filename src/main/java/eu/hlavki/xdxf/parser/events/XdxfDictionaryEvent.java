/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hlavki.xdxf.parser.events;

import eu.hlavki.xdxf.parser.data.Dictionary;
import java.util.EventObject;

/**
 *
 * @author hlavki
 */
public class XdxfDictionaryEvent extends EventObject {

    private static final long serialVersionUID = -5997566225611192690L;

    public XdxfDictionaryEvent(Dictionary src) {
        super(src);
    }

    @Override
    public Dictionary getSource() {
        return (Dictionary) super.getSource();
    }
}
