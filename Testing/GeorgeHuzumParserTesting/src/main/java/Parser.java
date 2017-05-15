import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public List<Article> parseElements () throws Exception{
        List<Article> articles = new ArrayList<Article>();

        Document document = Jsoup.connect("http://rss.cnn.com/rss/money_latest.rss").get();
        Elements rawArticles = document.select("item");

        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat outputDate = new SimpleDateFormat("yyyy-MM-dd");

        for(Element elements:rawArticles){
            String title = elements.select("title").first().text();
            String link = elements.select("link").first().text();
            String extractedDate = elements.select("pubDate").first().text().substring(4,17).trim().replaceAll(" ","-");
            String date = outputDate.format(inputFormat.parse(extractedDate));
            //Date d = inputFormat.parse(date);
            String content = elements.select("description").text().split("<img")[0];
            String mediaLink = elements.html().substring(rawArticles.get(0).html().indexOf("url=")+5,rawArticles.get(0).html().indexOf(".jpg")+4);

            articles.add(new Article(title,link,date,content,mediaLink));
        }

        return articles;
    }
}
