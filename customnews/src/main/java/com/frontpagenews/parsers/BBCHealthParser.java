package com.frontpagenews.parsers;

import com.frontpagenews.models.ArticleModel;
import com.frontpagenews.models.SourceModel;
import com.frontpagenews.services.ArticleService;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// @author AnaMaria

@Component
public class BBCHealthParser {
    String title;
    String content;
    Elements firstNews;
    Elements lastNews;
    Elements lastNews1;
    String urlArticle;
    String urlImg;
    String author;
    Date articleDate;
    String date;
    String contentDescriptionTags; //site-ul meu nu are tag-uri(sau nu le gasesc eu) asa ca iau cuvintele mai lungi de 5 din descrierea siteului drept tag-uri. 
    List<String> listUrlArticle=new ArrayList();
    List<String> tags = new ArrayList<String>();

    @Autowired
    ArticleService articleService;

    @Scheduled(initialDelay = 5000, fixedDelay=3600000)
    public void parse() throws IOException{
        //extrag linkurile articolelor
        Document doc = Jsoup.connect("http://www.bbc.com/news/health").get();
        listUrlArticle.clear();
        firstNews =doc.select("div.distinct-component-group.container-buzzard").select("div.buzzard-item").select("a.title-link");
        for(Element a: firstNews)
        {
            urlArticle=a.absUrl("href");
            listUrlArticle.add(urlArticle);
            //System.out.println("titlu:"+urlArticle);                
        } 
        //
        lastNews =doc.select("div.pigeon").select("div.pigeon-item__body").select("a.title-link");
        for(Element a: lastNews)
        {
            urlArticle=a.absUrl("href");
            listUrlArticle.add(urlArticle);
            //System.out.println("titluuuuuu:"+urlArticle);                
        } 
        //
        lastNews1 =doc.select("div.pigeon").select("div.pigeon__column.pigeon__column--b").select("a.title-link");
        for(Element a: lastNews1)
        {
            urlArticle=a.absUrl("href");
            listUrlArticle.add(urlArticle);
            //System.out.println("tttitlu:"+urlArticle);                
        } 
        for(String i :listUrlArticle)
        {
            System.out.println(i);
        }
        
        // iau fiecare url de articol din lista si extrag datele necesare
        for(String i :listUrlArticle)
        {
            Document article=Jsoup.connect(i).get();
            Elements divBody=article.select("div.story-body");
            Elements list_tags= article.select("meta[property=\"og:description\"]");

            //tags
            for(Element x: list_tags)
            {
                contentDescriptionTags=x.attr("content");
                System.out.println("description: "+ contentDescriptionTags);
                String[] str=contentDescriptionTags.split("\\s+");
                for (String str1 : str) {
                    if ((str1.length()) > 5) {
                        tags.add(str1);
                    }
                }
                for(String s: tags)
                    System.out.print(s+ ' ');
                    
            }
            System.out.println();
            for(Element t: divBody){
                //title
                title=t.select("h1.story-body__h1").text();
                System.out.println("title: "+title);

                //url image
                urlImg=t.select("img.js-image-replace").attr("src");
                System.out.println("img: "+urlImg);

                //content
                content=t.select("div.story-body__inner p:nth-child(n+3):not(:last-child)").text();
                System.out.println("content: "+content);

                //author
                //sunt unele articole care nu au un autor specificat asa ca pun autorul anonim
                author=t.select("div.byline :first-child").text();
                if(author.isEmpty())
                    author="Unknown";
                System.out.println("author: "+ author);

                //date
                date=t.select("div.date.date--v2").text();
                //System.out.println("date String: "+date);
                try{
                    DateFormat df = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
                    articleDate = df.parse(date);
                } catch  (ParseException e) {
                    e.printStackTrace();
                }
                System.out.println("date: "+articleDate);


                SourceModel source = new SourceModel();
                source.setSite(i);
                source.setDate(articleDate);
                source.setAuthor(author);

                ArticleModel article2 = new ArticleModel();
                article2.setTitle(title);
                article2.setContent(content);
                article2.setImageUrl(urlImg);
                article2.setTags(tags);
                article2.setSource(source);

                System.out.println (article2);
                try {
                    articleService.save(article2);
                }catch(Exception e)
                {
                    System.out.println(e.getMessage());
                }

            }
        }  //end for care parcurge liste de url-urile articolelor           
    }

    public static void main(String[] args) throws IOException {
        BBCHealthParser parser=new BBCHealthParser();
        parser.parse();
    }
    
}
