package com.frontpagenews.parsers;

import com.frontpagenews.APIs.TranslatorAPI.Language;
import com.frontpagenews.APIs.TranslatorAPI.TranslatorAPI;
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

import javax.swing.*;


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
    String tag;
    List<String> sourceTags = new ArrayList<String>();

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
        /*for(String i :listUrlArticle)
        {
            System.out.println(i);
        }*/
        
        // iau fiecare url de articol din lista si extrag datele necesare
        for(String i :listUrlArticle)
        {
            Document article=Jsoup.connect(i).get();
            Elements divBody=article.select("div.story-body");
            Elements list_tags= article.select("meta[property=\"og:description\"]");
            Summar summar=new Summar();
            //tags
            for(Element x: list_tags)
            {
                contentDescriptionTags=x.attr("content");
                //System.out.println("description: "+ contentDescriptionTags);
                String[] str=contentDescriptionTags.split("\\s+");
                for (String str1 : str) {
                    if ((str1.length()) > 5) {
                        sourceTags.add(str1);
                    }
                }
                //for(String s: sourceTags)
                    //System.out.print(s+ ' ');
                    
            }
            //System.out.println();
            for(Element t: divBody){
                //title
                title=t.select("h1.story-body__h1").text();
                //System.out.println("title: "+title);

                //url image
                urlImg=t.select("img.js-image-replace").attr("src");
                //System.out.println("img: "+urlImg);

                //content
                content=t.select("div.story-body__inner p:nth-child(n+3):not(:last-child)").text();
                //System.out.println("content: "+content);

                //author
                //sunt unele articole care nu au un autor specificat asa ca pun autorul anonim
                author=t.select("div.byline :first-child").text();
                if(author.isEmpty())
                    author="Unknown";
                //System.out.println("author: "+ author);

                //date
                date=t.select("div.date.date--v2").text();
                //System.out.println("date String: "+date);
                try{
                    DateFormat df = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
                    articleDate = df.parse(date);
                } catch  (ParseException e) {
                    //e.printStackTrace();
                }
                //System.out.println("date: "+articleDate);

                //detect article
                Language language = Language.ENGLISH;

                SourceModel source = new SourceModel();
                source.setSite(i);
                source.setDate(articleDate);
                source.setAuthor(author);

                ArticleModel article2 = new ArticleModel();
                article2.setTitle(title);
                article2.setContent(content);
                
                summar=new Summar(content);
                String shortSummary = summar.getShortSummary();
                String longSummary=summar.getLongSummary();
                article2.setShortSummary(shortSummary);
                article2.setLongSummary(longSummary);
                    
                article2.setContentLength(content.length());
                article2.setImageUrl(urlImg);
                if (urlImg.length() > 5) {
                    try {
                        URL url = new URL(urlImg);
                        Image image = new ImageIcon(url).getImage();
                        int imgWidth = image.getWidth(null);
                        int imgHeight = image.getHeight(null);
                        article2.setImageHeight(imgHeight);
                        article2.setImageWidth(imgWidth);
                    }
                    catch (Exception ex) {
                        article2.setImageHeight(0);
                        article2.setImageWidth(0);
                    }
                };
                article2.setTag("health");
                article2.setSourceTags(sourceTags);
                article2.setSource(source);
                article2.setLanguage(language.toString());

                //System.out.println (article2);
                try {
                    Language to1=Language.FRENCH,to2=Language.GERMAN,to3=Language.ITALIAN,to4=Language.SPANISH;
                    ArticleModel articleE = articleService.getByTitle(article2.getTitle());
                    if (articleE == null)
                        articleService.save(article2);

                    ArticleModel frenchArticle = new ArticleModel();
                    String f_title=article2.getTitle();
                    String f_content= article2.getContent();
                    String f_title2, f_content2, f_sumar2;
                    f_title2=TranslatorAPI.translate(f_title,language,to1);
                    frenchArticle.setTitle(f_title2);
                    articleE = articleService.getByTitle(frenchArticle.getTitle());
                    if (articleE == null) {
                        f_content2=TranslatorAPI.translate(f_content,language,to1);
                        frenchArticle.setContent(f_content2);
                        frenchArticle.setLanguage(to1.toString());
                        
                        f_sumar2=TranslatorAPI.translate(shortSummary,language,to1);
                        frenchArticle.setShortSummary(f_sumar2);
                        f_sumar2=TranslatorAPI.translate(longSummary,language,to1);
                        frenchArticle.setLongSummary(f_sumar2);
                        
                        frenchArticle.setImageWidth(article2.getImageWidth());
                        frenchArticle.setImageHeight(article2.getImageHeight());
                        frenchArticle.setSourceTags(article2.getSourceTags());
                        frenchArticle.setSource(article2.getSource());
                        frenchArticle.setContentLength(f_content.length());
                        frenchArticle.setImageUrl(article2.getImageUrl());
                        frenchArticle.setTag(article2.getTag());
                        frenchArticle.setVideoUrl(article2.getVideoUrl());
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
                        
                         f_sumar2=TranslatorAPI.translate(shortSummary,language,to1);
                        germanArticle.setShortSummary(f_sumar2);
                        f_sumar2=TranslatorAPI.translate(longSummary,language,to1);
                        germanArticle.setLongSummary(f_sumar2);
                        
                        germanArticle.setImageWidth(article2.getImageWidth());
                        germanArticle.setImageHeight(article2.getImageHeight());
                        germanArticle.setSourceTags(article2.getSourceTags());
                        germanArticle.setSource(article2.getSource());
                        germanArticle.setContentLength(f_content.length());
                        germanArticle.setImageUrl(article2.getImageUrl());
                        germanArticle.setTag(article2.getTag());
                        germanArticle.setVideoUrl(article2.getVideoUrl());
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
                        
                        f_sumar2=TranslatorAPI.translate(shortSummary,language,to1);
                        italianArticle.setShortSummary(f_sumar2);
                        f_sumar2=TranslatorAPI.translate(longSummary,language,to1);
                        italianArticle.setLongSummary(f_sumar2);
                        
                        italianArticle.setImageWidth(article2.getImageWidth());
                        italianArticle.setImageHeight(article2.getImageHeight());
                        italianArticle.setSourceTags(article2.getSourceTags());
                        italianArticle.setSource(article2.getSource());
                        italianArticle.setContentLength(f_content.length());
                        italianArticle.setImageUrl(article2.getImageUrl());
                        italianArticle.setTag(article2.getTag());
                        italianArticle.setVideoUrl(article2.getVideoUrl());
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
                        
                        f_sumar2=TranslatorAPI.translate(shortSummary,language,to1);
                        spanishArticle.setShortSummary(f_sumar2);
                        f_sumar2=TranslatorAPI.translate(longSummary,language,to1);
                        spanishArticle.setLongSummary(f_sumar2);
                        
                        spanishArticle.setImageWidth(article2.getImageWidth());
                        spanishArticle.setImageHeight(article2.getImageHeight());
                        spanishArticle.setSourceTags(article2.getSourceTags());
                        spanishArticle.setSource(article2.getSource());
                        spanishArticle.setContentLength(f_content.length());
                        spanishArticle.setImageUrl(article2.getImageUrl());
                        spanishArticle.setTag(article2.getTag());
                        spanishArticle.setVideoUrl(article2.getVideoUrl());
                        articleService.save(spanishArticle);
                    }
                }catch(Exception e)
                {
                    //System.out.println(e.getMessage());
                }

            }
        }  //end for care parcurge liste de url-urile articolelor           
    }

    public static void main(String[] args) throws IOException {
        BBCHealthParser parser=new BBCHealthParser();
        parser.parse();
    }
    
}
