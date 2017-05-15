package com.frontpagenews.parsers;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.frontpagenews.models.ArticleModel;
import com.frontpagenews.models.SourceModel;
import com.frontpagenews.services.ArticleService;

@Component
public class CNNMoneyParser {
    @Autowired
    ArticleService articleService;

    @Scheduled(initialDelay = 5000, fixedDelay=3600000)
    public void parseAll() {
        try {
            Document document = Jsoup.connect("http://rss.cnn.com/rss/money_latest.rss").get();
            Elements rawArticles = document.select("item");

            for (Element rawArts : rawArticles) {
                parse(rawArts);
            }
        } catch (Exception e){
            System.out.println (e.toString());
        }
    }


    private void parse(Element rawArticle){

        try {
            String title = rawArticle.select("title").first().text();

            String link = rawArticle.select("link").first().text();

            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy");
            SimpleDateFormat outputDate = new SimpleDateFormat("yyyy-MM-dd");
            String extractedDate = rawArticle.select("pubDate").first().text().substring(4,17).trim().replaceAll(" ","-");
            String date = outputDate.format(inputFormat.parse(extractedDate));

            String content = rawArticle.select("description").text().split("<img")[0];

            String mediaLink = rawArticle.html().substring(rawArticles.get(0).html().indexOf("url=")+5,rawArticles.get(0).html().indexOf(".jpg")+4);



            SourceModel source = new SourceModel();
            source.setSite(link);
            source.setDate(date);
            source.setAuthor(null);

            ArticleModel article = new ArticleModel();
            article.setTitle(title);
            article.setContent(content);
            article.setImageUrl(mediaLink);
            article.setTags(null);
            article.setSource(source);

            articleService.save(article);

        }catch (Exception e){
            System.out.println (e.toString());
        }
    }

    public static void main(String[] args){
        CNNMoneyParser p = new CNNMoneyParser();
        p.parseAll();
    }
}
