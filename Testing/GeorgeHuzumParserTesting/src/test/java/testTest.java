

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by huzum on 5/13/2017.
 */
public class testTest {
    @Test
    public void testAdd() throws Exception {

        Parser p = new Parser();

        List<Article> articles = p.parseElements();

        assertTrue(articles.size()>1);

    }

    @Test
    public void testAdd2() throws Exception{

        Parser p = new Parser();

        List<Article> articles = p.parseElements();

        for(Article art:articles){
            boolean containsDollar = art.getTitle().contains("$");
            assertTrue(!containsDollar);
        }

    }

}