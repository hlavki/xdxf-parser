package eu.hlavki.xdxf.parser;

import eu.hlavki.xdxf.parser.data.XDXFDictionary;
import eu.hlavki.xdxf.parser.event.XDXFArticleEvent;
import eu.hlavki.xdxf.parser.event.XDXFDictionaryEvent;
import eu.hlavki.xdxf.parser.event.XDXFEventListener;
import java.io.IOException;
import java.io.InputStream;
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
            in = getClass().getResourceAsStream("/test-dict.xdxf");
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
            articleCount++;
        }

        public int getArticleCount() {
            return articleCount;
        }

        public XDXFDictionary getDictionary() {
            return dictionary;
        }
    }
}
