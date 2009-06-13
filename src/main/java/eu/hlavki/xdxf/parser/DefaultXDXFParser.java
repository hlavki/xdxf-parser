/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author hlavki
 */
public class DefaultXDXFParser implements XDXFParser {

    private static final Logger log = Logger.getLogger(DefaultXDXFParser.class.getName());
    private List<XDXFEventListener> xdxfEventListeners;

    public DefaultXDXFParser() {
        xdxfEventListeners = new ArrayList<XDXFEventListener>(3);
    }

    public void parse(XDXFContext ctx, File file) throws ParseException {
        try {
            parse(ctx, new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new ParseException(e);
        }
    }

    public void parse(XDXFContext ctx, InputStream in) throws ParseException {
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
                ParserUtil.checkStartElement(xmlr, XDXFElement.XDXF);
                XDXFDictionary dict = new XDXFElementParser().parseElement(xmlr);
                fireXdxfDictionaryEvent(dict);
                ctx.setDictionary(dict);
                while (xmlr.hasNext()) {
                    xmlr.next();
                    if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) {
                        XDXFElement el = XDXFElement.fromName(xmlr.getName());
                        switch (el) {
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
                            case XDXF:
                                break;
                        }
                    } else if (ParserUtil.checkFor(XMLStreamConstants.END_ELEMENT, xmlr, XDXFElement.XDXF)) {
                        break;
                    } else {
                        if (xmlr.getEventType() != XMLStreamConstants.CHARACTERS) {
                            log.warning("Unknown type:" + xmlr.getEventType() + xmlr.getLocalName());
                        }
                    }
                }
                ParserUtil.checkEndElement(xmlr, XDXFElement.XDXF);
            } else {
                throw new ParseException("Invalid xml. Cannot find root element!");
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

    public void addSearchEventListener(XDXFEventListener listener) {
        xdxfEventListeners.add(listener);
    }

    public void removeSearchEventListener(XDXFEventListener listener) {
        xdxfEventListeners.remove(listener);
    }
}
