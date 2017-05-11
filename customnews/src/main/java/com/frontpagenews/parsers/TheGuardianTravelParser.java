package com.frontpagenews.parsers;

import com.frontpagenews.models.SourceModel;
import com.frontpagenews.models.ArticleModel;
import com.frontpagenews.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

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
      System.out.println("Exception caught when trying to connect to https://www.theguardian.com/us/travel");
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

                SourceModel source = new SourceModel();
                source.setSite(url);
                source.setDate(articleDate);
                source.setAuthor(articleAuthor);

                ArticleModel article = new ArticleModel();
                article.setTitle(title);
                article.setContent(formattedArticle.toString());
                article.setImageUrl(imageUrl);
                article.setTags(tagsList);
                article.setSource(source);

                articleService.save(article);
            }
        }
      }
    }
    catch(IOException excp){
      System.out.println("Exception caught when trying to connect to "+" "+url);
    }
  }

  public static void main(String[] args) {
    TheGuardianTravelParser travelParser = new TheGuardianTravelParser();
    travelParser.parseAllArticles();
  }
}
