package com.frontpagenews.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JokesParser {

    public static void main(String[] args) {

        Document doc;
        try {

            // just for one category, must be expanded to all
            // tags will be created after each category
            doc = Jsoup.connect("http://www.bancuri.net/Categoria_Legile_lui_Murphy-48.htm").get();

            // get page title
            String title = doc.title();
            System.out.println("title : " + title);

            // get all jokes, they are 
            Elements jokes = doc.select("span[class=S]");
            for (Element joke : jokes)
                System.out.println("Text : " + joke.text());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}