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

import static eu.hlavki.xdxf.parser.XDXFElement.*;
import eu.hlavki.xdxf.parser.model.XDXFArticle;
import eu.hlavki.xdxf.parser.model.XDXFDictionary;
import eu.hlavki.xdxf.parser.element.XDXFAbbreviationsElementParser;
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
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

public class DefaultXDXFParser implements XDXFParser {

    private static final Logger log = Logger.getLogger(DefaultXDXFParser.class.getName());
    private List<XDXFEventListener> xdxfEventListeners;
    private static final Set<XDXFElement> ROOT_ELEM = EnumSet.of(XDXF);

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
        xmlif.setProperty(XMLInputFactory.IS_COALESCING, Boolean.FALSE);
        xmlif.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
        XMLStreamReader xmlr = null;
        try {
            xmlr = xmlif.createXMLStreamReader(in);
            XDXFDictionary dict = null;
            fireXdxfStartEvent();
            while (xmlr.hasNext()) {
                xmlr.next();
                if (xmlr.getEventType() == XMLEvent.START_ELEMENT) {
                    XDXFElement el = XDXFElement.fromName(xmlr, ROOT_ELEM, true);
                    switch (el) {
                        case XDXF:
                            ParserUtil.assertStartElement(xmlr, XDXF);
                            dict = new XDXFElementParser().parseElement(xmlr);
                            fireXdxfDictionaryEvent(dict);
                            break;
                        case ABBREVIATIONS:
                            ParserUtil.assertStartElement(xmlr, ABBREVIATIONS);
                            dict.setAbbreviations(new XDXFAbbreviationsElementParser().parseElement(xmlr));

                            ParserUtil.assertEndElement(xmlr, ABBREVIATIONS);
                            fireXdxfDictionaryChangeEvent(dict);
                            break;
                        case XDXF_FULL_NAME:
                            ParserUtil.assertStartElement(xmlr, XDXF_FULL_NAME);
                            String fullName = new XDXFFullNameElementParser().parseElement(xmlr);
                            dict.setFullName(fullName);
                            ParserUtil.assertEndElement(xmlr, XDXF_FULL_NAME);
                            fireXdxfDictionaryChangeEvent(dict);
                            break;
                        case XDXF_DESCRIPTION:
                            ParserUtil.assertStartElement(xmlr, XDXF_DESCRIPTION);
                            String desc = new XDXFDescriptionElementParser().parseElement(xmlr);
                            dict.setDescription(desc);
                            ParserUtil.assertEndElement(xmlr, XDXF_DESCRIPTION);
                            fireXdxfDictionaryChangeEvent(dict);
                            break;
                        case ARTICLE:
                            ParserUtil.assertStartElement(xmlr, ARTICLE);
                            XDXFArticle article = new XDXFArticleElementParser().parseElement(xmlr);
                            ParserUtil.assertEndElement(xmlr, ARTICLE);
                            fireXdxfArticleEvent(article);
                            break;
                    }
                } else if (xmlr.getEventType() == XMLEvent.END_ELEMENT) {
                    ParserUtil.assertEndElement(xmlr, XDXF);
                }
            }
        } catch (XMLStreamException e) {
            throw new ParseException(e);
        } finally {
            try {
                fireXdxfFinishEvent();
            } catch (Exception e) {
                log.log(Level.SEVERE, "Exception occurs when firing onFinish event", e);
            }
            try {
                if (xmlr != null) {
                    xmlr.close();
                }
            } catch (XMLStreamException e) {
                log.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    public XDXFDictionary parseDictionary(File file) throws ParseException {
        try {
            return parseDictionary(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new ParseException(e);
        }
    }

    public XDXFDictionary parseDictionary(InputStream in) throws ParseException {
        XMLInputFactory xmlif = null;
        xmlif = XMLInputFactory.newInstance();
        xmlif.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.TRUE);
        xmlif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
        //set the IS_COALESCING property to true , if application desires to
        //get whole text data as one event.
        xmlif.setProperty(XMLInputFactory.IS_COALESCING, Boolean.FALSE);
        xmlif.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
        XDXFDictionary dict = null;
        XMLStreamReader xmlr = null;
        try {
            xmlr = xmlif.createXMLStreamReader(in);
            boolean finish = false;
            boolean[] parts = new boolean[]{false, false, false};
            fireXdxfStartEvent();
            while (xmlr.hasNext() && !finish) {
                xmlr.next();
                if (xmlr.getEventType() == XMLEvent.START_ELEMENT) {
                    XDXFElement el = XDXFElement.fromName(xmlr, ROOT_ELEM, true);
                    switch (el) {
                        case XDXF:
                            ParserUtil.assertStartElement(xmlr, XDXF);
                            dict = new XDXFElementParser().parseElement(xmlr);
                            fireXdxfDictionaryEvent(dict);
                            parts[0] = true;
                            break;
                        case XDXF_FULL_NAME:
                            ParserUtil.assertStartElement(xmlr, XDXF_FULL_NAME);
                            String fullName = new XDXFFullNameElementParser().parseElement(xmlr);
                            dict.setFullName(fullName);
                            ParserUtil.assertEndElement(xmlr, XDXF_FULL_NAME);
                            fireXdxfDictionaryChangeEvent(dict);
                            parts[1] = true;
                            break;
                        case XDXF_DESCRIPTION:
                            ParserUtil.assertStartElement(xmlr, XDXF_DESCRIPTION);
                            String desc = new XDXFDescriptionElementParser().parseElement(xmlr);
                            dict.setDescription(desc);
                            ParserUtil.assertEndElement(xmlr, XDXF_DESCRIPTION);
                            fireXdxfDictionaryChangeEvent(dict);
                            parts[2] = true;
                            break;
                    }
                } else if (xmlr.getEventType() == XMLEvent.END_ELEMENT) {
                    ParserUtil.assertEndElement(xmlr, XDXF);
                }
                finish = parts[0] && parts[1] && parts[2];
            }
        } catch (XMLStreamException e) {
            throw new ParseException(e);
        } finally {
            try {
                fireXdxfFinishEvent();
            } catch (Exception e) {
                log.log(Level.SEVERE, "Exception occurs when firing onFinish event", e);
            }
            try {
                if (xmlr != null) {
                    xmlr.close();
                }
            } catch (XMLStreamException e) {
                log.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        return dict;
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

    private void fireXdxfStartEvent() {
        for (XDXFEventListener listener : xdxfEventListeners) {
            listener.onStart();
        }
    }

    private void fireXdxfFinishEvent() {
        for (XDXFEventListener listener : xdxfEventListeners) {
            listener.onFinish();
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
