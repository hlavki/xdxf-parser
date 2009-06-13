/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hlavki.xdxf.parser.events;

/**
 *
 * @author hlavki
 */
public interface XdxfEventListener {

    void onDictionary(XdxfDictionaryEvent evt);

    void onDictionaryChange(XdxfDictionaryEvent evt);

    void onArticle(XdxfArticleEvent evt);
}
