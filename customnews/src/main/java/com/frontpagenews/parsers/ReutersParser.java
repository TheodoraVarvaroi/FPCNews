package com.frontpagenews.parsers;

import com.frontpagenews.APIs.TranslatorAPI.Language;
import com.frontpagenews.APIs.TranslatorAPI.TranslatorAPI;
import com.frontpagenews.models.ArticleModel;
import com.frontpagenews.models.SourceModel;
import com.frontpagenews.services.ArticleService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.frontpagenews.APIs.summar.Summar;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        articleModel.setSource(getSource(html));
        String f_image = getImageUrl(html);
        articleModel.setImageUrl(f_image);
        if (f_image.length() > 5) {
            try {
                URL url = new URL(f_image);
                Image image_ = new ImageIcon(url).getImage();
                int imgWidth = image_.getWidth(null);
                int imgHeight = image_.getHeight(null);
                articleModel.setImageHeight(imgHeight);
                articleModel.setImageWidth(imgWidth);
            }
            catch (Exception ex) {
                articleModel.setImageHeight(0);
                articleModel.setImageWidth(0);
            }
        };
        articleModel.setTag("politics");
        articleModel.setSourceTags(getTags(html));
        String content = getContent(html);
        Summar summar=new Summar(content);
        String summary = summar.getSummary();
        articleModel.setSummary(summary);
        articleModel.setContent(content);
        articleModel.setContentLength(content.length());

        //detect article language
        Language language = Language.ENGLISH;
        //language = TranslatorAPI.detectLanguage(content.substring(0, 500)).toString();
        articleModel.setLanguage(language.toString());

        //System.out.println(articleModel);
        try{
            Language to1=Language.FRENCH,to2=Language.GERMAN,to3=Language.ITALIAN,to4=Language.SPANISH;
            ArticleModel articleE = articleService.getByTitle(articleModel.getTitle());
            if (articleE == null)
                articleService.save(articleModel);

            ArticleModel frenchArticle = new ArticleModel();
            String f_title=articleModel.getTitle();
            String f_content=articleModel.getContent();
            String f_title2, f_content2, f_sumar2;
            f_title2= TranslatorAPI.translate(f_title,language,to1);
            frenchArticle.setTitle(f_title2);
            articleE = articleService.getByTitle(frenchArticle.getTitle());
            if (articleE == null) {
                f_content2=TranslatorAPI.translate(f_content,language,to1);
                frenchArticle.setContent(f_content2);
                frenchArticle.setLanguage(to1.toString());
                f_sumar2=TranslatorAPI.translate(summary,language,to1);
                frenchArticle.setSummary(f_sumar2);
                frenchArticle.setImageWidth(articleModel.getImageWidth());
                frenchArticle.setImageHeight(articleModel.getImageHeight());
                frenchArticle.setSourceTags(articleModel.getSourceTags());
                frenchArticle.setSource(articleModel.getSource());
                frenchArticle.setContentLength(f_content.length());
                frenchArticle.setImageUrl(articleModel.getImageUrl());
                frenchArticle.setTag(articleModel.getTag());
                frenchArticle.setVideoUrl(articleModel.getVideoUrl());
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
                germanArticle.setImageWidth(articleModel.getImageWidth());
                germanArticle.setImageHeight(articleModel.getImageHeight());
                germanArticle.setSourceTags(articleModel.getSourceTags());
                germanArticle.setSource(articleModel.getSource());
                germanArticle.setContentLength(f_content.length());
                germanArticle.setImageUrl(articleModel.getImageUrl());
                germanArticle.setTag(articleModel.getTag());
                germanArticle.setVideoUrl(articleModel.getVideoUrl());
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
                italianArticle.setImageWidth(articleModel.getImageWidth());
                italianArticle.setImageHeight(articleModel.getImageHeight());
                italianArticle.setSourceTags(articleModel.getSourceTags());
                italianArticle.setSource(articleModel.getSource());
                italianArticle.setContentLength(f_content.length());
                italianArticle.setImageUrl(articleModel.getImageUrl());
                italianArticle.setTag(articleModel.getTag());
                italianArticle.setVideoUrl(articleModel.getVideoUrl());
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
                spanishArticle.setImageWidth(articleModel.getImageWidth());
                spanishArticle.setImageHeight(articleModel.getImageHeight());
                spanishArticle.setSourceTags(articleModel.getSourceTags());
                spanishArticle.setSource(articleModel.getSource());
                spanishArticle.setContentLength(f_content.length());
                spanishArticle.setImageUrl(articleModel.getImageUrl());
                spanishArticle.setTag(articleModel.getTag());
                spanishArticle.setVideoUrl(articleModel.getVideoUrl());
                articleService.save(spanishArticle);
            }

        }
        catch (Exception e)
        {
            //System.out.println(e.getMessage());
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
        return html.substring(position +tag.length(),position2).replace("amp;","");
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