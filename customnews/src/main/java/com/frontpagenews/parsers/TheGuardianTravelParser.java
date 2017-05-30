package com.frontpagenews.parsers;

import com.frontpagenews.APIs.TranslatorAPI.TranslatorAPI;
import com.frontpagenews.APIs.TranslatorAPI.Language;
import com.frontpagenews.models.SourceModel;
import com.frontpagenews.models.ArticleModel;
import com.frontpagenews.services.ArticleService;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import com.frontpagenews.APIs.summar.Summar;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.io.IOException;

@Component
public class TheGuardianTravelParser {
  @Autowired
  private ArticleService articleService;

  @Scheduled(initialDelay = 5000, fixedDelay=3600000)
  public void parseAllArticles(){
    try {
      Document doc = Jsoup.connect("https://www.theguardian.com/us/travel").get();
      Elements linkList = doc.select(".fc-item__content a[href]");
      for (Element link : linkList) {
        String url = link.attr("abs:href");
        parseArticle(url);
      }
    }
    catch(IOException excp){
      //System.out.println("Exception caught when trying to connect to https://www.theguardian.com/us/travel");
    }
  }
  private void parseArticle(String url){
    try {
      Document doc = Jsoup.connect(url).get();
      //get title
      Element header1 = doc.select("h1").first();
      String title = header1.text();
      if (!title.contains("10 ")) { //
        //get content
        Elements content = doc.select("div[itemprop=\"articleBody\"]");
        Elements articleContent = content.select("h2,p");
        StringBuilder formattedArticle = new StringBuilder();
        int numberOfParagraphs = 0;
        for (Element articleElement : articleContent) {
          if (numberOfParagraphs <= 4) {
            formattedArticle.append(articleElement.text());
            formattedArticle.append("\n");
            if (articleElement.tagName().equals("p"))
              numberOfParagraphs++;
          } else {
            break;
          }
        }
        if (!formattedArticle.toString().isEmpty()) {
            //get image
            content = doc.select("div.gs-container");
            Element image = content.select("img").first();
            String imageUrl = image.attr("src");

            //get tags
            Elements tags = doc.select("meta[property=\"article:tag\"]");
            List<String> tagsList;
            tagsList = Arrays.asList(tags.attr("content").split("\\s*,\\s*"));
            if (!tagsList.get(0).isEmpty()) {
                //get author
                Elements author = doc.select("meta[name=\"author\"]");
                String articleAuthor = author.attr("content");
                if (articleAuthor.isEmpty()) {
                    author = doc.select("p.byline");
                    articleAuthor = author.text();
                    if (articleAuthor.isEmpty()) {
                        articleAuthor = "Guardian writers";
                    }
                }

                //get date
                Elements publishedDate = doc.select("meta[property=\"article:published_time\"]");
                String date = publishedDate.attr("content");
                Date articleDate = null;
                try {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    articleDate = new Date();

                    if (date.isEmpty()) {
                        date = dateFormat.format(articleDate);
                    }//every article will have the current date if one has not been found while parsing

                    articleDate = dateFormat.parse(date);

                } catch (ParseException excp) {
                    //do nothing, I already initialised the date with the current date
                }

                //detect article language
                Language language = Language.ENGLISH;

                SourceModel source = new SourceModel();
                source.setSite(url);
                source.setDate(articleDate);
                source.setAuthor(articleAuthor);

                ArticleModel article = new ArticleModel();
                article.setTitle(title);
                article.setContent(formattedArticle.toString());
                Summar summar=new Summar(formattedArticle.toString());
                String summary = summar.getSummary();
                article.setSummary(summary);
                article.setContentLength(formattedArticle.length());
                article.setImageUrl(imageUrl);
                if (imageUrl.length() != 0) {
                    try {
                        URL url_ = new URL(imageUrl);
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
                article.setTag("travel");
                article.setSourceTags(tagsList);
                article.setSource(source);
                article.setLanguage(language.toString());

                //System.out.println(article);
                try {
                    Language to1=Language.FRENCH,to2=Language.GERMAN,to3=Language.ITALIAN,to4=Language.SPANISH;
                    ArticleModel articleE = articleService.getByTitle(article.getTitle());
                    if (articleE == null)
                        articleService.save(article);

                    ArticleModel frenchArticle = new ArticleModel();
                    String f_title=article.getTitle();
                    String f_content= article.getContent();
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
            }
        }
      }
    }
    catch(IOException excp){
      //System.out.println("Exception caught when trying to connect to "+" "+url);
    }
  }

  public static void main(String[] args) {
    TheGuardianTravelParser travelParser = new TheGuardianTravelParser();
    travelParser.parseAllArticles();
  }
}
