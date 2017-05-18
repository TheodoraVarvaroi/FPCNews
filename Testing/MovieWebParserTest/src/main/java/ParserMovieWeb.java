import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class ParserMovieWeb {

    ArticleService articleService;
    ArrayList<ArticleModel> articles = new ArrayList<> ();

      public List<ArticleModel> parseAll() {
    
    try {
            
            Document doc = Jsoup.connect("http://movieweb.com/movie-news/").get();
            Elements links = doc.select("div.media-body>a ");  
            for (int i=0;i<links.size();i++)
            {
            //    System.out.println( links.get(i).attr("abs:href"));
                  parse(links.get(i));
            }
        } catch (IOException e){
            System.out.println (e.toString());
        }
          return articles;
      }
    
    
    private void parse(Element link){

        
    String url=link.attr("abs:href"); 
    String currentSite=url;

    Document article;
    try {
    article=Jsoup.connect(url).get();
  
    Elements title=article.select("div.container>h1");
    String currentTitle=title.text();
//    System.out.println(currentTitle);
       
    Elements author = article.select("div.author>a");
    String currentAuthor = author.text();
    if(currentAuthor.isEmpty())
        currentAuthor="MovieWeb";
 //   System.out.println(currentAuthor);
       
    Elements date= article.select("meta[property=\"article:published_time\"]");
    String data=date.attr("content");
    DateFormat format = new SimpleDateFormat("yyyy-mm-dd"); 
    Date dateOfPublishing=null;
        try {
                 dateOfPublishing = format.parse(data);
           //     System.out.println(dateOfPublishing);
                
            } catch (ParseException ex) 
                {
                    Logger.getLogger(ParserMovieWeb.class.getName()).log(Level.SEVERE, null, ex);
                }
         
    Elements img = article.select("meta[property=\"og:image\"]");
    String currentImg = img.attr("content");
  //  System.out.println(currentImg); 
      
      
    Elements bodyArticle = article.select("article.articleBody");
    String currentArticle = bodyArticle.text();
        //    System.out.println(currentArticle); 
        
    Elements tags = article.select("meta[property=\"article:tag\"]");
    List<String> currentTags = new ArrayList<String>();
        for (int i=0;i<tags.size();i++) 
          {
            currentTags.add(tags.get(i).attr("content"));
        //    System.out.println(tags.get(i).attr("content"));
           } 
        
         
            SourceModel source = new SourceModel();
            source.setSite(currentSite);
            source.setDate(dateOfPublishing);
            source.setAuthor(currentAuthor);

            ArticleModel articol = new ArticleModel();
            articol.setTitle(currentTitle);
            articol.setImageUrl(currentImg);
            articol.setContent(currentArticle);
            articol.setTags(currentTags);
            articol.setSource(source);

        articles.add(articol);
            
        } catch (IOException ex) {
            Logger.getLogger(ParserMovieWeb.class.getName()).log(Level.SEVERE, null, ex);
        }
    
           // System.out.println();
            
        }
        
        
    public static void main(String[] args){
        ParserMovieWeb p = new ParserMovieWeb();
        p.parseAll();
     
     
    }
        
        
}
