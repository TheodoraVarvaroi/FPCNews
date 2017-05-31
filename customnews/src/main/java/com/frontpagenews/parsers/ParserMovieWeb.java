
package com.frontpagenews.parsers;

import com.frontpagenews.APIs.TranslatorAPI.TranslatorAPI;
import com.frontpagenews.APIs.TranslatorAPI.Language;
import com.frontpagenews.models.ArticleModel;
import com.frontpagenews.models.SourceModel;
import com.frontpagenews.services.ArticleService;
import com.frontpagenews.APIs.summar.Summar;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
                    //Logger.getLogger(ParserMovieWeb.class.getName()).log(Level.SEVERE, null, ex);
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

            Summar summar=new Summar();
            //detect article language
            Language language = Language.ENGLISH;
            
            SourceModel source = new SourceModel();
            source.setSite(currentSite);
            source.setDate(dateOfPublishing);
            source.setAuthor(currentAuthor);

            ArticleModel articol = new ArticleModel();
            articol.setTitle(currentTitle);
            articol.setImageUrl(currentImg);
            if (currentImg.length() > 5) {
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
            summar=new Summar(currentArticle);
            String summary = summar.getSummary();
            articol.setSummary(summary);
            articol.setContentLength(currentArticle.length());
            articol.setTag("movie");
            articol.setSourceTags(currentTags);
            articol.setSource(source);
            articol.setLanguage(language.toString());
            //System.out.println(articol);
            Language to1=Language.FRENCH,to2=Language.GERMAN,to3=Language.ITALIAN,to4=Language.SPANISH;
            try {
                ArticleModel articleE = articleService.getByTitle(articol.getTitle());
                if (articleE == null)
                    articleService.save(articol);

                ArticleModel frenchArticle = new ArticleModel();
                String f_title=articol.getTitle();
                String f_content= articol.getContent();
                String f_title2,f_content2, f_sumar2;
                f_title2=TranslatorAPI.translate(f_title,language,to1);
                frenchArticle.setTitle(f_title2);
                articleE = articleService.getByTitle(frenchArticle.getTitle());
                if (articleE == null) {
                    f_content2=TranslatorAPI.translate(f_content,language,to1);
                    frenchArticle.setContent(f_content2);
                    frenchArticle.setLanguage(to1.toString());
                    f_sumar2=TranslatorAPI.translate(summary,language,to1);
                    frenchArticle.setSummary(f_sumar2);
                    frenchArticle.setImageWidth(articol.getImageWidth());
                    frenchArticle.setImageHeight(articol.getImageHeight());
                    frenchArticle.setSourceTags(articol.getSourceTags());
                    frenchArticle.setSource(articol.getSource());
                    frenchArticle.setContentLength(f_content.length());
                    frenchArticle.setImageUrl(articol.getImageUrl());
                    frenchArticle.setTag(articol.getTag());
                    frenchArticle.setVideoUrl(articol.getVideoUrl());
                    articleService.save(frenchArticle);
                }

                ArticleModel germanArticle = new ArticleModel();
                f_title2=TranslatorAPI.translate(f_title,language,to2);
                germanArticle.setTitle(f_title2);
                articleE = articleService.getByTitle(germanArticle.getTitle());
                if (articleE == null) {
                    f_content2=TranslatorAPI.translate(f_content,language,to2);
                    germanArticle.setContent(f_content2);
                    germanArticle.setLanguage(to2.toString());
                    f_sumar2=TranslatorAPI.translate(summary,language,to2);
                    germanArticle.setSummary(f_sumar2);
                    germanArticle.setImageWidth(articol.getImageWidth());
                    germanArticle.setImageHeight(articol.getImageHeight());
                    germanArticle.setSourceTags(articol.getSourceTags());
                    germanArticle.setSource(articol.getSource());
                    germanArticle.setContentLength(f_content.length());
                    germanArticle.setImageUrl(articol.getImageUrl());
                    germanArticle.setTag(articol.getTag());
                    germanArticle.setVideoUrl(articol.getVideoUrl());
                    articleService.save(germanArticle);
                }

                ArticleModel italianArticle = new ArticleModel();
                f_title2=TranslatorAPI.translate(f_title,language,to3);
                italianArticle.setTitle(f_title2);
                articleE = articleService.getByTitle(italianArticle.getTitle());
                if (articleE == null) {
                    f_content2=TranslatorAPI.translate(f_content,language,to3);
                    italianArticle.setContent(f_content2);
                    italianArticle.setLanguage(to3.toString());
                    f_sumar2=TranslatorAPI.translate(summary,language,to3);
                    italianArticle.setSummary(f_sumar2);
                    italianArticle.setImageWidth(articol.getImageWidth());
                    italianArticle.setImageHeight(articol.getImageHeight());
                    italianArticle.setSourceTags(articol.getSourceTags());
                    italianArticle.setSource(articol.getSource());
                    italianArticle.setContentLength(f_content.length());
                    italianArticle.setImageUrl(articol.getImageUrl());
                    italianArticle.setTag(articol.getTag());
                    italianArticle.setVideoUrl(articol.getVideoUrl());
                    articleService.save(italianArticle);
                }

                ArticleModel spanishArticle = new ArticleModel();
                f_title2=TranslatorAPI.translate(f_title,language,to4);
                spanishArticle.setTitle(f_title2);
                articleE = articleService.getByTitle(spanishArticle.getTitle());
                if (articleE == null) {
                    f_content2=TranslatorAPI.translate(f_content,language,to4);
                    spanishArticle.setContent(f_content2);
                    spanishArticle.setLanguage(to4.toString());
                    f_sumar2=TranslatorAPI.translate(summary,language,to4);
                    spanishArticle.setSummary(f_sumar2);
                    spanishArticle.setImageWidth(articol.getImageWidth());
                    spanishArticle.setImageHeight(articol.getImageHeight());
                    spanishArticle.setSourceTags(articol.getSourceTags());
                    spanishArticle.setSource(articol.getSource());
                    spanishArticle.setContentLength(f_content.length());
                    spanishArticle.setImageUrl(articol.getImageUrl());
                    spanishArticle.setTag(articol.getTag());
                    spanishArticle.setVideoUrl(articol.getVideoUrl());
                    articleService.save(spanishArticle);
                }

            } catch ( Exception e){
                //System.out.println (e.toString());
            }
            
        } catch (IOException ex) {
            //Logger.getLogger(ParserMovieWeb.class.getName()).log(Level.SEVERE, null, ex);
        }
           // System.out.println();
        }
        
        
    public static void main(String[] args){
        ParserMovieWeb p = new ParserMovieWeb();
        p.parseAll();
     
     
    }
        
        
}
