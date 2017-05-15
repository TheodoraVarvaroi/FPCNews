import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Onu on 5/15/2017.
 */
public class testClass {
    @Test
    public void testAdd() throws Exception {

        BBCHealthParser p = new BBCHealthParser();

        List<ArticleModel> articles =  p.parse();

        assertTrue(articles.size()>1);

    }

    @Test
    public void testAdd2() throws Exception{

        BBCHealthParser p = new BBCHealthParser();

        List<ArticleModel> articles =  p.parse();

        for(ArticleModel art:articles){
            boolean containsDollar = art.getTitle().contains("$");
            assertFalse(containsDollar);
        }
    }
    @Test
    //checking if the url is Good
    public void testImageUrl() throws Exception {
        BBCHealthParser p = new BBCHealthParser();

        List<ArticleModel> articles =  p.parse();

        for(ArticleModel art:articles){
            String imageUrl = art.getImageUrl() ;
            boolean goodUrl = imageUrl.matches("^$|^(http|https)://.*$");
            assertTrue(goodUrl);

        }
    }
    @Test
    public void checkSourceUrl () throws  Exception {
        BBCHealthParser p = new BBCHealthParser();
        List<ArticleModel> articles =  p.parse();

        for(ArticleModel art:articles){
            String url = String.valueOf(art.getSource());
            String content = String.valueOf(art.getContent());

            boolean goodUrl = url.matches("^Site: (http|https)://.*$");
            assertTrue(!content.isEmpty() && goodUrl );
        }

    }

}
