import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;


/**
 * Created by Jitca Beniamin on 16/05/2017.
 */
public class testTheGuardianTravelParser {
    @Test
    public void testAdd() throws Exception {
        TheGuardianTravelParser p = new TheGuardianTravelParser();
        List<ArticleModel> articles =  p.parseAllArticles();
        assertTrue(articles.size()>1);
    }

    @Test
    public void testContainsDollar() throws Exception{
        TheGuardianTravelParser p = new TheGuardianTravelParser();
        List<ArticleModel> articles =  p.parseAllArticles();

        for(ArticleModel art:articles){
            boolean containsDollar = art.getTitle().contains("$");
            assertFalse(containsDollar);
        }
    }

    @Test
    //checking if the url is Good
    public void testImageUrl() throws Exception {
        TheGuardianTravelParser p = new TheGuardianTravelParser();
        List<ArticleModel> articles =  p.parseAllArticles();

        for(ArticleModel art:articles){
            String imageUrl = art.getImageUrl() ;
            boolean goodUrl = imageUrl.matches("^(http|https|ftp)://.*$");
            assertTrue(goodUrl);
        }
    }

    @Test
    public void checkSourceUrl () throws  Exception {
        TheGuardianTravelParser p = new TheGuardianTravelParser();
        List<ArticleModel> articles =  p.parseAllArticles();

        for(ArticleModel art:articles){
            String url = String.valueOf(art.getSource());
            String content = String.valueOf(art.getContent());
            boolean goodUrl = url.matches("^Site: (http|https)://.*$");
            assertTrue(!content.isEmpty() && goodUrl );
        }
    }

    // test image parser

}
