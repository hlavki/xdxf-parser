package eu.hlavki.xdxf.parser;

import eu.hlavki.xdxf.parser.data.XDXFDictionary;
import eu.hlavki.xdxf.parser.event.XDXFArticleEvent;
import eu.hlavki.xdxf.parser.event.XDXFDictionaryEvent;
import eu.hlavki.xdxf.parser.event.XDXFEventListener;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sk.datalan.util.timer.ExecuteTimer;
import static org.junit.Assert.*;

public class XdxfParserTest {

    @Before
    public void beforeTest() {
    }

    @After
    public void afterTest() {
    }

    @Test
    public void parseSampleDictionary() {
        InputStream in = null;
        try {
            XDXFParser parser = new DefaultXDXFParser();
            DictionaryListener listener = new DictionaryListener();
            parser.addXDXFEventListener(listener);
            ExecuteTimer timer = new ExecuteTimer();
            timer.start();
            in = getClass().getResourceAsStream("/full-impl-test.xdxf");
            parser.parse(in);
            timer.stop();
            System.out.println("Articles count: " + listener.getArticleCount());
            System.out.println("Parsing time: " + timer);
            XDXFDictionary dictionary = listener.getDictionary();
            assertNotNull(dictionary);
            assertNotNull(dictionary.getSrcLanguage());
            assertNotNull(dictionary.getTargetLanguage());
            assertNotNull(dictionary.getFullName());
            assertNotNull(dictionary.getFormat());
            assertTrue(listener.getArticleCount() > 0);
        } catch (ParseException e) {
            e.printStackTrace();
            fail();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class DictionaryListener implements XDXFEventListener {

        private int articleCount = 0;
        private XDXFDictionary dictionary;

        public void onDictionary(XDXFDictionaryEvent evt) {
            this.dictionary = evt.getSource();
        }

        public void onDictionaryChange(XDXFDictionaryEvent evt) {
            this.dictionary = evt.getSource();
        }

        public void onArticle(XDXFArticleEvent evt) {
            System.out.println("Article: " + evt.getSource());
            articleCount++;
        }

        public int getArticleCount() {
            return articleCount;
        }

        public XDXFDictionary getDictionary() {
            return dictionary;
        }
    }

    @Test
    public void testInputFile() {
        InputStream in = getClass().getResourceAsStream("/full-impl-test.xdxf");
        XMLInputFactory xmlif = XMLInputFactory.newInstance();
        xmlif.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.TRUE);
        xmlif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
        //set the IS_COALESCING property to true , if application desires to
        //get whole text data as one event.
        xmlif.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
        xmlif.setProperty(XMLInputFactory.IS_COALESCING, Boolean.FALSE);
        try {
            XMLStreamReader xmlr = xmlif.createXMLStreamReader(in);
            while (xmlr.hasNext()) {
                xmlr.next();
                int event = xmlr.getEventType();
                switch (event) {
                    case XMLEvent.START_ELEMENT:
                        System.out.println("Start Element: " + xmlr.getName());
                        break;
                    case XMLEvent.CHARACTERS:
                        System.out.println("Text: " + xmlr.getText().trim());
                        break;
                    case XMLEvent.END_ELEMENT:
                        System.out.println("End Element: " + xmlr.getName());
                }
            }
        } catch (XMLStreamException ex) {
            ex.printStackTrace();
        }

    }
}
