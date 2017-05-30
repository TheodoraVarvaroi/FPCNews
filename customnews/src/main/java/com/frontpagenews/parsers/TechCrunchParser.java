/**
 * Created by dangh on 14.05.2017.
 */
package com.frontpagenews.parsers;

import com.frontpagenews.APIs.TranslatorAPI.TranslatorAPI;
import com.frontpagenews.APIs.TranslatorAPI.Language;
import com.frontpagenews.models.ArticleModel;
import com.frontpagenews.models.SourceModel;
import com.frontpagenews.services.ArticleService;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.MongoException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.frontpagenews.APIs.summar.Summar;
import javax.swing.*;

@Component
public class TechCrunchParser {
    @Autowired
    ArticleService articleService;

    @Scheduled(initialDelay = 5000, fixedDelay=3600000)

    public void parseAll() {
        try {
            Document doc = Jsoup.connect("http://feeds.feedburner.com/TechCrunch/").get();
            Elements links = doc.select("item");

            for (Element link : links) {
                String url= link.getElementsByTag("link").text();
                parse(url);
            }
        } catch (IOException e){
            //System.out.println (e.toString());
        }
    }


    private void parse(String url) throws IOException {
        try{
        Document doc=Jsoup.connect(url).get();
        String f_site= url;
        Elements title = doc.select("h1");
        String f_title = title.text();
        //System.out.println(f_title);

        Elements content = doc.getElementsByClass("article-entry text");
        String f_content = content.text();
        //System.out.println(f_content);

        Elements image= content.select("img");
        String f_image= image.attr("src");
        //System.out.println(f_image);

        Elements tags = doc.select("meta[name=sailthru.tags]");
        List<String> f_tags = new ArrayList<String>();
        String baseString= tags.attr("content");
        //System.out.println(baseString);
        String token="";
        for(int i=0;i<baseString.length();i++){
            if(baseString.charAt(i)==',')
            {
                if(token!="") {
                    f_tags.add(token);
                    //System.out.println(token);
                }
                token="";
                i++;
            }
            else {
                token += baseString.charAt(i);
            }
        }

        Elements author = doc.select("meta[name = author]");
        String f_author = author.attr("content");
        //System.out.println(f_author);

        Elements date= doc.getElementsByClass("timestamp");
        String d = date.attr("datetime");
        Date f_date = null;
        try{
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            f_date = df.parse(d);
            String newDateString = df.format(f_date);
            //System.out.println(newDateString);
            //System.out.println(f_date);

        } catch  (ParseException e) {
            //e.printStackTrace();
        }

            //detect article language
            Language language = Language.ENGLISH;

            SourceModel source = new SourceModel();
            source.setSite(f_site);
            source.setDate(f_date);
            source.setAuthor(f_author);

            ArticleModel article = new ArticleModel();
            article.setTitle(f_title);
            article.setContent(f_content);
            Summar summar=new Summar(f_content);
            String summary = summar.getSummary();
            article.setSummary(summary);
            article.setContentLength(f_content.length());
            article.setImageUrl(f_image);
            if (f_image.length() != 0) {
                try {
                    URL url_ = new URL(f_image);
                    Image image_ = new ImageIcon(url_).getImage();
                    int imgWidth = image_.getWidth(null);
                    int imgHeight = image_.getHeight(null);
                    article.setImageHeight(imgHeight);
                    article.setImageWidth(imgWidth);
                }
                catch (Exception ex) {
                    article.setImageHeight(0);
                    article.setImageWidth(0);
                }
            };
            article.setTag("technology");
            article.setSourceTags(f_tags);
            article.setSource(source);
            article.setLanguage(language.toString());
            //System.out.println (article);
            try {
                Language to1=Language.FRENCH,to2=Language.GERMAN,to3=Language.ITALIAN,to4=Language.SPANISH;
                ArticleModel articleE = articleService.getByTitle(article.getTitle());
                if (articleE == null)
                    articleService.save(article);

                ArticleModel frenchArticle = new ArticleModel();
                String f_title2, f_content2, f_sumar2;
                f_title2=TranslatorAPI.translate(f_title,language,to1);
                frenchArticle.setTitle(f_title2);
                articleE = articleService.getByTitle(frenchArticle.getTitle());
                if (articleE == null) {
                    f_content2=TranslatorAPI.translate(f_content,language,to1);
                    frenchArticle.setContent(f_content2);
                    frenchArticle.setLanguage(to1.toString());
                    f_sumar2=TranslatorAPI.translate(summary,language,to1);
                    frenchArticle.setSummary(f_sumar2);
                    frenchArticle.setImageWidth(article.getImageWidth());
                    frenchArticle.setImageHeight(article.getImageHeight());
                    frenchArticle.setSourceTags(article.getSourceTags());
                    frenchArticle.setSource(article.getSource());
                    frenchArticle.setContentLength(f_content.length());
                    frenchArticle.setImageUrl(article.getImageUrl());
                    frenchArticle.setTag(article.getTag());
                    frenchArticle.setVideoUrl(article.getVideoUrl());
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
                    germanArticle.setImageWidth(article.getImageWidth());
                    germanArticle.setImageHeight(article.getImageHeight());
                    germanArticle.setSourceTags(article.getSourceTags());
                    germanArticle.setSource(article.getSource());
                    germanArticle.setContentLength(f_content.length());
                    germanArticle.setImageUrl(article.getImageUrl());
                    germanArticle.setTag(article.getTag());
                    germanArticle.setVideoUrl(article.getVideoUrl());
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
                    italianArticle.setImageWidth(article.getImageWidth());
                    italianArticle.setImageHeight(article.getImageHeight());
                    italianArticle.setSourceTags(article.getSourceTags());
                    italianArticle.setSource(article.getSource());
                    italianArticle.setContentLength(f_content.length());
                    italianArticle.setImageUrl(article.getImageUrl());
                    italianArticle.setTag(article.getTag());
                    italianArticle.setVideoUrl(article.getVideoUrl());
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
                    spanishArticle.setImageWidth(article.getImageWidth());
                    spanishArticle.setImageHeight(article.getImageHeight());
                    spanishArticle.setSourceTags(article.getSourceTags());
                    spanishArticle.setSource(article.getSource());
                    spanishArticle.setContentLength(f_content.length());
                    spanishArticle.setImageUrl(article.getImageUrl());
                    spanishArticle.setTag(article.getTag());
                    spanishArticle.setVideoUrl(article.getVideoUrl());
                    articleService.save(spanishArticle);
                }

            } catch ( Exception e){
                //System.out.println (e.toString());
            }

        }catch (IOException e){
            //System.out.println (e.toString());
        }
        //System.out.println("\n");
    }

    public static void main(String[] args){
        TechCrunchParser p = null;
        p = new TechCrunchParser();
        p.parseAll();
    }
}

