package com.frontpagenews.parsers;

import com.frontpagenews.models.ArticleModel;
import com.frontpagenews.models.SourceModel;
import com.frontpagenews.services.ArticleService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ervin on 5/11/2017.
 */
@Component
public class ReutersParser {
    @Autowired
    ArticleService articleService;

    @Scheduled(initialDelay = 5000, fixedDelay=3600000)
    public void parseAll() throws IOException {
       ArrayList<String> adrese=new ArrayList<String>();
        Document doc = Jsoup.connect("http://www.reuters.com/politics").get();
        String html=doc.html();
        String url="http://www.reuters.com";
        int position=0,position2=0;
        String tag="<li><a href=\"/article/";

        while((position=html.indexOf(tag,position))!=-1)
        {
            position2=html.indexOf('"',position+tag.length());
            pageParser(url+html.substring(position+tag.length()-9,position2));
            position=position+tag.length();

        }
    }
    public void pageParser(String adresa)throws IOException
    {
        Document doc = Jsoup.connect(adresa).get();
        String html=doc.html();
        ArticleModel articleModel=new ArticleModel();
        articleModel.setTitle(getTitle(html));
       // System.out.println("Titlu:"+getTitle(html));

        articleModel.setSource(getSource(html));
        articleModel.setImageUrl(getImageUrl(html));
    //    System.out.println("ImageUrl:"+getImageUrl(html));
        articleModel.setTags(getTags(html));
      //  System.out.println(getTags(html).toString());
        articleModel.setContent(getContent(html));
      //  System.out.println("Content:"+getContent(html));
      //  System.out.println("Site:"+articleModel.getSource().getSite());
      //  System.out.println("Author:"+articleModel.getSource().getAuthor());
        try{
           articleService.save(articleModel);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public SourceModel getSource(String html)
    {
        SourceModel sourceModel=new SourceModel();
        Date data_lucrare=new Date();
        sourceModel.setDate(data_lucrare);
        String tag="<meta name=\"Author\" content=\"";
        int position=html.indexOf(tag);
        sourceModel.setAuthor(html.substring(position+tag.length(),html.indexOf('"',position+tag.length()+1)));
        tag="<meta property=\"og:url\" content=\"";
        position=html.indexOf(tag);
        sourceModel.setSite(html.substring(position+tag.length(),html.indexOf('"',position+tag.length()+1)));
        return sourceModel;
    }
    public List<String> getTags(String html)
    {
        List<String> tags=new ArrayList<String>();
        String tag="<meta name=\"keywords\" content=\"";
        String subcontent=html.substring(html.indexOf(tag)+tag.length(),html.indexOf('"',html.indexOf(tag)+tag.length()));
        int position;
        while((position=subcontent.indexOf(','))!=-1)
        {
            tags.add(subcontent.substring(0,position));
            subcontent=subcontent.substring(position+1);
        }
        return tags;
    }
    public String getImageUrl(String html)
    {
        String tag="<link rel=\"image_src\" href=\"";
        int position=html.indexOf(tag);
        int position2=html.indexOf('"',position+tag.length());
        return html.substring(position,position2);
    }
    public String getTitle(String html)
    {
        String tag="<meta property=\"og:title\" content=\"";
        int position=html.indexOf(tag);
        String title=html.substring(position+tag.length(),html.indexOf('"',position+tag.length()));
        return title;
    }
    public String getContent(String html)
    {
        StringBuilder sb=new StringBuilder();
        int position=0,span=1,position2;
        String tag="<span id=\"article-text\">";
        position=html.indexOf(tag);
        position=position+tag.length();
        while(position<html.length())
        {
            if(span==0)
                break;
            if(html.substring(position,position+6).equalsIgnoreCase("</span"))
            {
                position=position+6;
                span--;
            }
            if(html.substring(position,position+5).equalsIgnoreCase("<span"))
            {
                position=position+5;
                span++;
            }
            position++;
        }
        html=html.substring(html.indexOf(tag),position);
        html=html.replaceAll("<p>","");
        html=html.replaceAll("</p>","");
        position=html.indexOf("<span class=\"articleLocation\">");
        position2=html.indexOf("</span>",position);
        String sub=html.substring(position,position2+7);
        html=html.replaceAll(sub,"");
        position=html.indexOf("<span class=\"article-prime\">");
        html=html.substring(position+28);
        html=html.replaceAll("</span>","");
        while((position=html.indexOf("<span"))!=-1)
        {
            position2=html.indexOf(">",position);
            sub=html.substring(position,position2+1);
            html=html.replaceAll(sub,"");
        }
        return html;

    }
}
