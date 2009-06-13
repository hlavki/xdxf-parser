/*
 * DefaultXDXFParser.java
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

import eu.hlavki.xdxf.parser.data.XDXFArticle;
import eu.hlavki.xdxf.parser.data.XDXFDictionary;
import eu.hlavki.xdxf.parser.element.XDXFArticleElementParser;
import eu.hlavki.xdxf.parser.element.XDXFDescriptionElementParser;
import eu.hlavki.xdxf.parser.element.XDXFElementParser;
import eu.hlavki.xdxf.parser.element.XDXFFullNameElementParser;
import eu.hlavki.xdxf.parser.event.XDXFArticleEvent;
import eu.hlavki.xdxf.parser.event.XDXFDictionaryEvent;
import eu.hlavki.xdxf.parser.event.XDXFEventListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Michal Hlavac <hlavki@hlavki.eu>
 */
public class DefaultXDXFParser implements XDXFParser {

    private static final Logger log = Logger.getLogger(DefaultXDXFParser.class.getName());
    private List<XDXFEventListener> xdxfEventListeners;

    public DefaultXDXFParser() {
        xdxfEventListeners = new ArrayList<XDXFEventListener>(3);
    }

    /**
     * {@inheritDoc}
     */
    public void parse(File file) throws ParseException {
        try {
            parse(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new ParseException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void parse(InputStream in) throws ParseException {
        XMLInputFactory xmlif = null;
        xmlif = XMLInputFactory.newInstance();
        xmlif.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.TRUE);
        xmlif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
        //set the IS_COALESCING property to true , if application desires to
        //get whole text data as one event.
        xmlif.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
        xmlif.setProperty(XMLInputFactory.IS_COALESCING, Boolean.FALSE);
        try {
            XMLStreamReader xmlr = xmlif.createXMLStreamReader(in);
            XDXFDictionary dict = null;
            while (xmlr.hasNext()) {
                xmlr.next();
                if (xmlr.getEventType() == XMLEvent.START_ELEMENT) {
                    XDXFElement el = XDXFElement.fromName(xmlr.getName());
                    switch (el) {
                        case XDXF:
                            ParserUtil.checkStartElement(xmlr, XDXFElement.XDXF);
                            dict = new XDXFElementParser().parseElement(xmlr);
                            fireXdxfDictionaryEvent(dict);
                            break;
                        case XDXF_FULL_NAME:
                            ParserUtil.checkStartElement(xmlr, XDXFElement.XDXF_FULL_NAME);
                            String fullName = new XDXFFullNameElementParser().parseElement(xmlr);
                            dict.setFullName(fullName);
                            ParserUtil.checkEndElement(xmlr, XDXFElement.XDXF_FULL_NAME);
                            fireXdxfDictionaryChangeEvent(dict);
                            break;
                        case XDXF_DESCRIPTION:
                            ParserUtil.checkStartElement(xmlr, XDXFElement.XDXF_DESCRIPTION);
                            String desc = new XDXFDescriptionElementParser().parseElement(xmlr);
                            dict.setDescription(desc);
                            ParserUtil.checkEndElement(xmlr, XDXFElement.XDXF_DESCRIPTION);
                            fireXdxfDictionaryChangeEvent(dict);
                            break;
                        case ARTICLE:
                            ParserUtil.checkStartElement(xmlr, XDXFElement.ARTICLE);
                            XDXFArticle article = new XDXFArticleElementParser().parseElement(xmlr);
                            ParserUtil.checkEndElement(xmlr, XDXFElement.ARTICLE);
                            fireXdxfArticleEvent(article);
                            break;
                    }
                } else if (xmlr.getEventType() == XMLEvent.END_ELEMENT) {
                    ParserUtil.checkEndElement(xmlr, XDXFElement.XDXF);
                }
            }
        } catch (XMLStreamException e) {
            throw new ParseException(e);
        }
    }

    private void fireXdxfDictionaryEvent(XDXFDictionary dictionary) {
        XDXFDictionaryEvent evt = new XDXFDictionaryEvent(dictionary);
        for (XDXFEventListener listener : xdxfEventListeners) {
            listener.onDictionary(evt);
        }
    }

    private void fireXdxfDictionaryChangeEvent(XDXFDictionary dictionary) {
        XDXFDictionaryEvent evt = new XDXFDictionaryEvent(dictionary);
        for (XDXFEventListener listener : xdxfEventListeners) {
            listener.onDictionaryChange(evt);
        }
    }

    private void fireXdxfArticleEvent(XDXFArticle article) {
        XDXFArticleEvent evt = new XDXFArticleEvent(article);
        for (XDXFEventListener listener : xdxfEventListeners) {
            listener.onArticle(evt);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void addXDXFEventListener(XDXFEventListener listener) {
        xdxfEventListeners.add(listener);
    }

    /**
     * {@inheritDoc}
     */
    public void removeXDXFEventListener(XDXFEventListener listener) {
        xdxfEventListeners.remove(listener);
    }
}
