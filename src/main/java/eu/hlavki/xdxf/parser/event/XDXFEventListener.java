/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hlavki.xdxf.parser.event;

/**
 *
 * @author hlavki
 */
public interface XDXFEventListener {

    void onDictionary(XDXFDictionaryEvent evt);

    void onDictionaryChange(XDXFDictionaryEvent evt);

    void onArticle(XDXFArticleEvent evt);
}
