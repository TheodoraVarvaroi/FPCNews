package com.frontpagenews.parsers;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.net.MalformedURLException;

import com.frontpagenews.APIs.TranslatorAPI;
import com.frontpagenews.APIs.YandexTranslatorAPI.language.Language;
import com.frontpagenews.models.ArticleModel;
import com.frontpagenews.models.SourceModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.frontpagenews.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.swing.*;

import static com.frontpagenews.APIs.YandexTranslatorAPI.language.Language.*;

@Component
public class EurosportParser {
    @Autowired
    ArticleService articleService;
    ArrayList<ArticleModel> articles=new ArrayList<ArticleModel>();

    @Scheduled(initialDelay = 5000000, fixedDelay=3600000)
    public void parseAll() throws IOException {
        get_all_articles();
        insertAllArticles();
    }
    public void get_all_articles() throws IOException {
        String adress="http://www.eurosport.com/football/";
        ArrayList<String>links=new ArrayList<String>();
        links=getAllLinks(adress);
        SourceModel sourceModel;
        Date date=new Date();
        for(int i=0;i<links.size();i++)
        {
            ArticleModel articol=new ArticleModel();
            sourceModel=new SourceModel();
            Document doc= Jsoup.connect(links.get(i)).get();
            String content = getArticleContent(doc.html());
            //detect article language
            Language language = Language.ENGLISH;
            articol.setContent(getArticleContent(content));
            articol.setContentLength(content.length());
            String imageUrl = getImage(doc.html());
            articol.setImageUrl(imageUrl);
            if (imageUrl.length() != 0) {
                try {
                    URL url = new URL(imageUrl);
                    Image image = new ImageIcon(url).getImage();
                    int imgWidth = image.getWidth(null);
                    int imgHeight = image.getHeight(null);
                    articol.setImageHeight(imgHeight);
                    articol.setImageWidth(imgWidth);
                }
                catch (Exception ex) {
                    articol.setImageHeight(0);
                    articol.setImageWidth(0);
                }
            };
            articol.setTitle(getTitle(doc.html()));
            sourceModel.setSite(links.get(i));
            sourceModel.setDate(date);
            articol.setSource(sourceModel);
            articol.setTag("sport");
            articol.setSourceTags(getTags(doc.html()));
            articol.setLanguage(language.toString());
            articles.add(articol);

            Language to1=FRENCH,to2=GERMAN,to3=ITALIAN,to4=SPANISH;

            articol.setTitle(TranslatorAPI.translate(articol.getTitle(),language,to1));
            articol.setContent(TranslatorAPI.translate(articol.getContent(),language,to1));
            articles.add(articol);

            articol.setTitle(TranslatorAPI.translate(articol.getTitle(),language,to2));
            articol.setContent(TranslatorAPI.translate(articol.getContent(),language,to2));
            articles.add(articol);

            articol.setTitle(TranslatorAPI.translate(articol.getTitle(),language,to3));
            articol.setContent(TranslatorAPI.translate(articol.getContent(),language,to3));
            articles.add(articol);

            articol.setTitle(TranslatorAPI.translate(articol.getTitle(),language,to4));
            articol.setContent(TranslatorAPI.translate(articol.getContent(),language,to4));
            articles.add(articol);
        }
    }
    private ArrayList<String> getAllLinks(String adress) throws IOException {
        ArrayList<String> links=new ArrayList<String>();
        Document doc=Jsoup.connect(adress).get();
        String htmlCode=doc.html();
        String tag="<div class=\"storylist-container__main-title None\">";
        for(int i=0;i<htmlCode.length()-tag.length()-1;i++)
        {
            if(htmlCode.substring(i, i+tag.length()).equalsIgnoreCase(tag)==true)
            {
                i=i+tag.length();
                while(htmlCode.charAt(i)!='"')
                    i++;
                StringBuilder sb=new StringBuilder();
                i++;
                while(htmlCode.charAt(i)!='"')
                {
                    sb.append(htmlCode.charAt(i));
                    i++;
                }
                links.add("http://eurosport.com"+sb.toString());
            }
        }
        return links;
    }
    public List<String> getTags(String htmlCode)
    {
        List<String> tags=new ArrayList<String>();
        int position=0,position2=0,position3=0;
        String tag="<a class=\"autolink\"";
        while((position=findFirstPostionStartWith(htmlCode,tag,position))!=-1)
        {
            position2=findFirstPostionStartWith(htmlCode,">",position+tag.length())+1;
            position3=findFirstPostionStartWith(htmlCode,"<",position2);
            tags.add(htmlCode.substring(position2,position3));
            position=position+tag.length();
        }
        return tags;
    }
    public void insertAllArticles()
    {
        for(int i=0;i<articles.size();i++)
        {

            try{
                articleService.save(articles.get(i));
            }
            catch(Exception e)
            {
                //System.out.println(e.getMessage());
            }
        }
    }
    private String getTitle(String htmlCode)
    {
        String tag="<h1 class=\"storyfull__header-title-main\">";
        int k=findFirstPosition(htmlCode,tag);
        tag="</h1>";
        int k2=findFirstPostionStartWith(htmlCode,tag,k);
        tag="<h1 class=\"storyfull__header-title-main\">";
        return htmlCode.substring(k+tag.length(),k2);
    }
    private String getArticleContent(String html)
    {
        StringBuilder sb=new StringBuilder();
        String tag4="<p class=\"storyfull__paragraph\">";
        String tag5="</p>";
        for(int i=0;i<html.length()-tag4.length();i++)
        {
            if(html.substring(i,i+tag4.length()).equalsIgnoreCase(tag4)==true)
            {
                while(html.substring(i,i+tag5.length()).equalsIgnoreCase(tag5)==false)
                {
                    sb.append(html.charAt(i));
                    i++;
                }
            }
        }
        String subcontent=sb.toString();
        subcontent=deleteTag(subcontent,"<p class=\"storyfull__paragraph\">");
        subcontent=deleteTag(subcontent,"<strong>");
        subcontent=deleteTag(subcontent,"<em>");
        subcontent=subcontent.replaceAll("[*]","");
        subcontent=subcontent.replaceAll(" - - - ","");
        String tag="<a";
        String tag2="</a>";
        sb=new StringBuilder();
        for(int i=0;i<subcontent.length()-tag.length()-1;i++)
        {
            if(subcontent.substring(i,i+tag.length()).equalsIgnoreCase(tag)==true)
            {
                while(subcontent.charAt(i)!='>')
                {
                    i++;
                }
                i++;
            }
            if(subcontent.substring(i,i+tag2.length()).equalsIgnoreCase(tag2)==true)
            {
                i=i+tag2.length();
            }
            sb.append(subcontent.charAt(i));
        }
        sb.append('.');
        return sb.toString();
    }
    private String getImage(String html) throws MalformedURLException, IOException
    {
        String tag="<div class=\"storyfull__ng-picture\">";
        int k=findFirstPosition(html,tag);
        k=k+tag.length();
        while(html.charAt(k)!='"')
        {
            k++;
        }
        StringBuilder sb=new StringBuilder();
        k++;
        while(html.charAt(k)!='"')
        {
            sb.append(html.charAt(k));
            k++;
        }
        String adresa=sb.toString();
        return adresa;
    }
    private int findFirstPosition(String html ,String tag)
    {
        int k=-1;
        for(int i=0;i<html.length()-tag.length()-1;i++)
        {
            if(html.substring(i,i+tag.length()).equalsIgnoreCase(tag)==true)
            {
                k=i;
                break;
            }
        }
        return k;
    }
    private int findFirstPostionStartWith(String html ,String tag ,int index)
    {
        int k=-1;
        for(int i=index;i<html.length()-tag.length()-1;i++)
        {
            if(html.substring(i,i+tag.length()).equalsIgnoreCase(tag)==true)
            {
                k=i;
                break;
            }
        }
        return k;
    }
    private String deleteTag(String htmlCode,String tag)
    {
        int position=-1;
        position=findFirstPosition(htmlCode,tag);
        if(position==-1)
            return htmlCode;
        StringBuilder sb=new StringBuilder();
        sb.append(htmlCode.substring(0,position));
        int position2=1;
        while(tag.charAt(position2)!=' '&& tag.charAt(position2)!='>')
            position2++;
        String tag2="</"+tag.substring(1,position2)+">";
        position2=findFirstPosition(htmlCode,tag2);
        if(position2!=-1)
        {
            sb.append(htmlCode.substring(position+tag.length(),position2));
            sb.append(htmlCode.substring(position2+tag2.length(),htmlCode.length()));
        }
        else
        {
            sb.append(htmlCode.substring(position+tag.length(),htmlCode.length()));
        }
        if(findFirstPosition(sb.toString(),tag)!=-1)
        {
            return deleteTag(sb.toString(),tag);
        }
        return sb.toString();
    }
}
