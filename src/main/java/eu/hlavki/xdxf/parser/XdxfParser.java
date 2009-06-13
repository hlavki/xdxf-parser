/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hlavki.xdxf.parser;

import eu.hlavki.xdxf.parser.events.XdxfEventListener;
import java.io.File;
import java.io.InputStream;

/**
 *
 * @author hlavki
 */
public interface XdxfParser {

    void parse(XdxfContext context, InputStream in) throws ParseException;

    void parse(XdxfContext context, File file) throws ParseException;

    public void addSearchEventListener(XdxfEventListener listener);

    public void removeSearchEventListener(XdxfEventListener listener);
}
