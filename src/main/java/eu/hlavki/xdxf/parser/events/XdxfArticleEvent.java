/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hlavki.xdxf.parser.events;

import eu.hlavki.xdxf.parser.data.XdxfArticle;
import java.util.EventObject;

/**
 *
 * @author hlavki
 */
public class XdxfArticleEvent extends EventObject {

    private static final long serialVersionUID = 1L;

    public XdxfArticleEvent(XdxfArticle src) {
        super(src);
    }

    @Override
    public XdxfArticle getSource() {
        return (XdxfArticle) super.getSource();
    }
}
