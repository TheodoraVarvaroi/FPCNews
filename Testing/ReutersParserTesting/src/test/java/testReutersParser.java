import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;


/**
 * Created by Jitca Beniamin on 16/05/2017.
 */
public class testReutersParser {
    @Test
    public void testAdd() throws Exception {
        ReutersParser p = new ReutersParser();
        List<ArticleModel> articles =  p.parseAll();
        assertTrue(articles.size()>1);
    }

    @Test
    public void testContainsDollar() throws Exception{
        ReutersParser p = new ReutersParser();
        List<ArticleModel> articles =  p.parseAll();
        for(ArticleModel art:articles){
            boolean containsDollar = art.getTitle().contains("$");
            assertFalse(containsDollar);
        }
    }

    @Test
    //checking if the url is Good
    public void testImageUrl() throws Exception {
        ReutersParser p = new ReutersParser();
        List<ArticleModel> articles =  p.parseAll();

        for(ArticleModel art : articles){
            String imageUrl = art.getImageUrl() ;
            boolean goodUrl = imageUrl.matches("^(http|https|ftp)://.*$");
            assertTrue(goodUrl);
        }
    }

    @Test
    public void checkSourceUrl () throws  Exception {
        ReutersParser p = new ReutersParser();
        List<ArticleModel> articles =  p.parseAll();

        for(ArticleModel art:articles){
            String url = String.valueOf(art.getSource());
            String content = String.valueOf(art.getContent());
            boolean goodUrl = url.matches("^Site: (http|https)://.*$");
            assertTrue(!content.isEmpty() && goodUrl );
        }
    }

}