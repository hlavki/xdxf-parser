/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hlavki.xdxf.parser;

import eu.hlavki.xdxf.parser.event.XDXFEventListener;
import java.io.File;
import java.io.InputStream;

/**
 *
 * @author hlavki
 */
public interface XDXFParser {

    void parse(XDXFContext context, InputStream in) throws ParseException;

    void parse(XDXFContext context, File file) throws ParseException;

    public void addSearchEventListener(XDXFEventListener listener);

    public void removeSearchEventListener(XDXFEventListener listener);
}
