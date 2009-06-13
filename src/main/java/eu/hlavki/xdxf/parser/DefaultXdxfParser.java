/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.hlavki.xdxf.parser;

import eu.hlavki.xdxf.parser.data.XdxfArticle;
import eu.hlavki.xdxf.parser.data.Dictionary;
import eu.hlavki.xdxf.parser.element.XdxfArticleElementParser;
import eu.hlavki.xdxf.parser.element.XdxfDescriptionElementParser;
import eu.hlavki.xdxf.parser.element.XdxfElementParser;
import eu.hlavki.xdxf.parser.element.XdxfFullNameElementParser;
import eu.hlavki.xdxf.parser.events.XdxfArticleEvent;
import eu.hlavki.xdxf.parser.events.XdxfDictionaryEvent;
import eu.hlavki.xdxf.parser.events.XdxfEventListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author hlavki
 */
public class DefaultXdxfParser implements XdxfParser {

    private static final Logger log = Logger.getLogger(DefaultXdxfParser.class.getName());
    private List<XdxfEventListener> xdxfEventListeners;

    public DefaultXdxfParser() {
        xdxfEventListeners = new ArrayList<XdxfEventListener>(3);
    }

    public void parse(XdxfContext ctx, File file) throws ParseException {
        try {
            parse(ctx, new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new ParseException(e);
        }
    }

    public void parse(XdxfContext ctx, InputStream in) throws ParseException {
        XMLInputFactory xmlif = null;
        try {
            xmlif = XMLInputFactory.newInstance();
            xmlif.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.TRUE);
            xmlif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
            //set the IS_COALESCING property to true , if application desires to
            //get whole text data as one event.
            xmlif.setProperty(XMLInputFactory.IS_COALESCING, Boolean.FALSE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            XMLStreamReader xmlr = xmlif.createXMLStreamReader(in);
            if (xmlr.hasNext()) {
                xmlr.next();
                ParserUtil.checkStartElement(xmlr, XdxfElement.XDXF);
                Dictionary dict = new XdxfElementParser().parseElement(xmlr);
                fireXdxfDictionaryEvent(dict);
                ctx.setDictionary(dict);
                while (xmlr.hasNext()) {
                    xmlr.next();
                    if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) {
                        XdxfElement el = XdxfElement.fromName(xmlr.getName());
                        switch (el) {
                            case XDXF_FULL_NAME:
                                ParserUtil.checkStartElement(xmlr, XdxfElement.XDXF_FULL_NAME);
                                String fullName = new XdxfFullNameElementParser().parseElement(xmlr);
                                dict.setFullName(fullName);
                                ParserUtil.checkEndElement(xmlr, XdxfElement.XDXF_FULL_NAME);
                                fireXdxfDictionaryChangeEvent(dict);
                                break;
                            case XDXF_DESCRIPTION:
                                ParserUtil.checkStartElement(xmlr, XdxfElement.XDXF_DESCRIPTION);
                                String desc = new XdxfDescriptionElementParser().parseElement(xmlr);
                                dict.setDescription(desc);
                                ParserUtil.checkEndElement(xmlr, XdxfElement.XDXF_DESCRIPTION);
                                fireXdxfDictionaryChangeEvent(dict);
                                break;
                            case ARTICLE:
                                ParserUtil.checkStartElement(xmlr, XdxfElement.ARTICLE);
                                XdxfArticle article = new XdxfArticleElementParser().parseElement(xmlr);
                                ParserUtil.checkEndElement(xmlr, XdxfElement.ARTICLE);
                                fireXdxfArticleEvent(article);
                                break;
                            case XDXF:
                                break;
                        }
                    } else if (ParserUtil.checkFor(XMLStreamConstants.END_ELEMENT, xmlr, XdxfElement.XDXF)) {
                        break;
                    } else {
                        if (xmlr.getEventType() != XMLStreamConstants.CHARACTERS) {
                            log.warning("Unknown type:" + xmlr.getEventType() + xmlr.getLocalName());
                        }
                    }
                }
                ParserUtil.checkEndElement(xmlr, XdxfElement.XDXF);
            } else {
                throw new ParseException("Invalid xml. Cannot find root element!");
            }
        } catch (XMLStreamException e) {
            throw new ParseException(e);
        }
    }

    private void fireXdxfDictionaryEvent(Dictionary dictionary) {
        XdxfDictionaryEvent evt = new XdxfDictionaryEvent(dictionary);
        for (XdxfEventListener listener : xdxfEventListeners) {
            listener.onDictionary(evt);
        }
    }

    private void fireXdxfDictionaryChangeEvent(Dictionary dictionary) {
        XdxfDictionaryEvent evt = new XdxfDictionaryEvent(dictionary);
        for (XdxfEventListener listener : xdxfEventListeners) {
            listener.onDictionaryChange(evt);
        }
    }

    private void fireXdxfArticleEvent(XdxfArticle article) {
        XdxfArticleEvent evt = new XdxfArticleEvent(article);
        for (XdxfEventListener listener : xdxfEventListeners) {
            listener.onArticle(evt);
        }
    }

    public void addSearchEventListener(XdxfEventListener listener) {
        xdxfEventListeners.add(listener);
    }

    public void removeSearchEventListener(XdxfEventListener listener) {
        xdxfEventListeners.remove(listener);
    }
}
