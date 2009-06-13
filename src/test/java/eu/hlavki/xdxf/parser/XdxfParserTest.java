package eu.hlavki.xdxf.parser;

import eu.hlavki.xdxf.parser.data.XdxfDictionary;
import eu.hlavki.xdxf.parser.events.XdxfArticleEvent;
import eu.hlavki.xdxf.parser.events.XdxfDictionaryEvent;
import eu.hlavki.xdxf.parser.events.XdxfEventListener;
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
        try {
            XdxfParser parser = new DefaultXdxfParser();
            DictionaryListener listener = new DictionaryListener();
            parser.addSearchEventListener(listener);
            XdxfContext ctx = new XdxfContext();
            ExecuteTimer timer = new ExecuteTimer();
            timer.start();
            parser.parse(ctx, XdxfParserTest.class.getResourceAsStream("/test-dict.xdxf"));
            timer.stop();
            System.out.println("Articles count: " + listener.getArticleCount());
            System.out.println("Parsing time: " + timer);
            XdxfDictionary dictionary = listener.getDictionary();
            assertNotNull(dictionary);
            assertNotNull(dictionary.getSrcLanguage());
            assertNotNull(dictionary.getTargetLanguage());
            assertNotNull(dictionary.getFullName());
            assertNotNull(dictionary.getFormat());
            assertTrue(listener.getArticleCount() > 0);
        } catch (ParseException e) {
            e.printStackTrace();
            fail();
        }
    }

    private static class DictionaryListener implements XdxfEventListener {

        private int articleCount = 0;
        private XdxfDictionary dictionary;

        public void onDictionary(XdxfDictionaryEvent evt) {
            this.dictionary = evt.getSource();
        }

        public void onDictionaryChange(XdxfDictionaryEvent evt) {
            this.dictionary = evt.getSource();
        }

        public void onArticle(XdxfArticleEvent evt) {
            articleCount++;
        }

        public int getArticleCount() {
            return articleCount;
        }

        public XdxfDictionary getDictionary() {
            return dictionary;
        }
    }
}
