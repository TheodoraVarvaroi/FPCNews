
package com.frontpagenews.parsers;

import com.frontpagenews.APIs.TranslatorAPI;
import com.frontpagenews.APIs.YandexTranslatorAPI.language.Language;
import com.frontpagenews.models.ArticleModel;
import com.frontpagenews.models.SourceModel;
import com.frontpagenews.services.ArticleService;
import com.mongodb.DB;
import com.mongodb.DBCollection;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.MongoException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class ParserMovieWeb {
    @Autowired
    ArticleService articleService;

    @Scheduled(initialDelay = 5000, fixedDelay=3600000)
      public void parseAll() {
    
    try {
            
            Document doc = Jsoup.connect("http://movieweb.com/movie-news/").get();
            Elements links = doc.select("div.media-body>a ");  
            for (int i=0;i<links.size();i++)
            {
            //    System.out.println( links.get(i).attr("abs:href"));
                  parse(links.get(i));
            }
        } catch (IOException e){
            //System.out.println (e.toString());
        }
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

            //detect article language
            String language = "ENGLISH";
            //language = TranslatorAPI.detectLanguage(currentArticle.substring(0, 500)).toString();

            SourceModel source = new SourceModel();
            source.setSite(currentSite);
            source.setDate(dateOfPublishing);
            source.setAuthor(currentAuthor);

            ArticleModel articol = new ArticleModel();
            articol.setTitle(currentTitle);
            articol.setImageUrl(currentImg);
            if (currentImg.length() != 0) {
                try {
                    URL url_ = new URL(currentImg);
                    Image image_ = new ImageIcon(url_).getImage();
                    int imgWidth = image_.getWidth(null);
                    int imgHeight = image_.getHeight(null);
                    articol.setImageHeight(imgHeight);
                    articol.setImageWidth(imgWidth);
                }
                catch (Exception ex) {
                    articol.setImageHeight(0);
                    articol.setImageWidth(0);
                }
            };
            articol.setContent(currentArticle);
            articol.setContentLength(currentArticle.length());
            articol.setTag("movie");
            articol.setSourceTags(currentTags);
            articol.setSource(source);
            articol.setLanguage(language);
            //System.out.println(articol);
            try {
                articleService.save(articol);
            } catch ( MongoException e){
                //System.out.println (e.toString());
            }
            
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
