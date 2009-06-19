/*
 * InvalidElementException.java
 *
 * Copyright (c) 2009 Michal HLavac <hlavki@hlavki.eu>. All rights reserved.
 *
 * This file is part of XDXF Parser (jar).
 *
 * XDXF Parser (jar) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * XDXF Parser (jar) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with XDXF Parser (jar).  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.hlavki.xdxf.parser;

import eu.hlavki.xdxf.parser.event.XDXFEventListener;
import eu.hlavki.xdxf.parser.model.XDXFDictionary;
import java.io.File;
import java.io.InputStream;

/**
 * Denfines event based parser interface. You can add|remove one or more listneners using
 * {@link addXDXFEventListener(XDXFEventListener)}|{@link removeXDXFEventListener(XDXFEventListener)} methods.<br/>
 * Every registered listener is notified about new events from xdxf dictionary objects such as dictionary, aricle,
 * abbreviations.<br/><br/>
 * Parser sample usage:<br/>
 * <pre>
 * InputStream in = null;
 * try  {
 *     XDXFParser parser = new DefaultXDXFParser();
 *     DictionaryListener listener = new DictionaryListener();
 *     parser.addXDXFEventListener(listener);
 *     in = getClass().getResourceAsStream("/test-dict.xdxf");
 *     parser.parse(in);
 * } catch (ParseException e} {
 *    log.log(Level.SEVERE, e.getMessage(), e);
 * } finally {
 *     try {
 *         if (in != null) in.close();
 *     } catch (IOException e) {
 *         // should never happened
 *     }
 * }
 * </pre>
 * {@code DictionaryListener} sample code:<br/>
 * <pre>
 *     private static class DictionaryListener implements XDXFEventListener {
 *
 *         private int articleCount = 0;
 *         private XDXFDictionary dictionary;
 *
 *         public void onDictionary(XDXFDictionaryEvent evt) {
 *             this.dictionary = evt.getSource();
 *         }
 *
 *         public void onDictionaryChange(XDXFDictionaryEvent evt) {
 *             this.dictionary = evt.getSource();
 *         }
 *
 *         public void onArticle(XDXFArticleEvent evt) {
 *             articleCount++;
 *         }
 *
 *         public int getArticleCount() {
 *             return articleCount;
 *         }
 *
 *         public XDXFDictionary getDictionary() {
 *             return dictionary;
 *         }
 *     }
 * </pre>
 * @author Michal Hlavac <hlavki@hlavki.eu>
 */
public interface XDXFParser {

    /**
     * Parse xdxf dictionary file. You can listen on events while parser is reading file. For more information
     * about events see {@link XDXFEventListener}.
     * @param in input stream for xdxf dictionary. Stream is not closed after parsing!
     * @throws ParseException parse exception is thrown when error occurs while parsing xdxf file
     * e.g. invalid xdxf format or bug ;)
     */
    void parse(InputStream in) throws ParseException;

    /**
     * Parse xdxf dictionary file. You can listen on events while parser is reading file. For more information
     * about events see {@link XDXFEventListener}.
     * @param file xdxf dictionary file
     * @throws ParseException parse exception is thrown when error occurs while parsing xdxf file
     * e.g. invalid xdxf format or bug ;)
     */
    void parse(File file) throws ParseException;

    /**
     * Parse xdxf file only for dictionary information represented by {@link XDXFDictionary}.
     * Articles are ignored. This operation should  be constantly fast for xdxf of every size.
     * @param in input stream
     * @return dictionary information (name, description, source language, target language)
     * @throws ParseException parse exception is thrown when error occurs while parsing xdxf file
     */
    XDXFDictionary parseDictionary(InputStream in) throws ParseException;

    /**
     * Parse xdxf file only for dictionary information represented by {@link XDXFDictionary}.
     * Articles are ignored. This operation should  be constantly fast for xdxf of every size.
     * @param file input file
     * @return dictionary information (name, description, source language, target language)
     * @throws ParseException parse exception is thrown when error occurs while parsing xdxf file
     */
    XDXFDictionary parseDictionary(File file) throws ParseException;

    /**
     * Register event listener. For more information about events see {@link XDXFEventListener}
     * @param listener registered listener
     */
    public void addXDXFEventListener(XDXFEventListener listener);

    /**
     * Unregister event listener. For more information about events see {@link XDXFEventListener}
     * @param listener unregistered listener
     */
    public void removeXDXFEventListener(XDXFEventListener listener);
}
