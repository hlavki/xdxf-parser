package eu.hlavki.xdxf.parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
            XdxfContext ctx = new XdxfContext();
            parser.parse(ctx, XdxfParserTest.class.getResourceAsStream("/test-dict.xdxf"));
        } catch (ParseException e) {
            e.printStackTrace();
            fail();
        }
    }
}
