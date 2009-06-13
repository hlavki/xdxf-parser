/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hlavki.xdxf.parser.event;

import eu.hlavki.xdxf.parser.data.XDXFArticle;
import java.util.EventObject;

/**
 *
 * @author hlavki
 */
public class XDXFArticleEvent extends EventObject {

    private static final long serialVersionUID = 1L;

    public XDXFArticleEvent(XDXFArticle src) {
        super(src);
    }

    @Override
    public XDXFArticle getSource() {
        return (XDXFArticle) super.getSource();
    }
}
