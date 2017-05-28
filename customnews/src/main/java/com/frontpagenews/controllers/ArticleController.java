package com.frontpagenews.controllers;

import com.frontpagenews.models.ArticleModel;
import com.frontpagenews.services.ArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8181")
@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    ArticleService articleService;

//    @Configuration
//    @EnableWebMvc
//    public class WebConfig extends WebMvcConfigurerAdapter {
//
//        @Override
//        public void addCorsMappings(CorsRegistry registry) {
//            registry.addMapping("/api/**")
//                    .allowedOrigins("*");
//        }
//    }

    @RequestMapping(method= RequestMethod.GET)
    public List<ArticleModel> getAllArticles() {
        return articleService.getAll();
    }

    @RequestMapping(method=RequestMethod.POST)
    public ArticleModel createArticle(@Valid @RequestBody ArticleModel admin) {
        return articleService.save(admin);
    }

    @RequestMapping(value="/page/{page}/{language}", method=RequestMethod.GET)
    public List<ArticleModel> getArticlesOnPage(@PathVariable("page") String page, @PathVariable("language") String language) {
        if(page.equals("0"))
            return new ArrayList<>();
        if (!(language.equals("en") || language.equals("fr") || language.equals("es") || language.equals("de") || language.equals("it")))
            return new ArrayList<>();
        int pagestart = (Integer.parseInt(page) - 1) * 10;
        List<ArticleModel> list = articleService.getAllSorted(language);
        if (pagestart > list.size())
            return new ArrayList<>();
        return list.subList(pagestart, Math.min(pagestart+10, list.size()));
    }

    @RequestMapping(value="/tags/{tags}/{language}", method=RequestMethod.GET)
    public List<ArticleModel> getArticlesWithTags(@PathVariable("tags") String tags, @PathVariable("language") String language) {
        if (!(language.equals("en") || language.equals("es") || language.equals("de") || language.equals("it")))
            return new ArrayList<>();
        List<String> tagsList = Arrays.asList(tags.split("&"));
        return articleService.getByTagInSorted(tagsList, language);
    }

    @RequestMapping(value="/tags/{tags}/page/{page}/{language}", method=RequestMethod.GET)
    public List<ArticleModel> getArticlesWithTagsOnPage(@PathVariable("page") String page, @PathVariable("tags") String tags, @PathVariable("language") String language) {
        List<String> tagsList = Arrays.asList(tags.split("&"));
        if(page.equals("0"))
            return new ArrayList<>();
        if (!(language.equals("en") || language.equals("fr") || language.equals("es") || language.equals("de") || language.equals("it")))
            return new ArrayList<>();
        int pagestart = (Integer.parseInt(page) - 1) * 10;
        List<ArticleModel> list = articleService.getByTagInSorted(tagsList, language);
        if (pagestart > list.size())
            return new ArrayList<>();
        return list.subList(pagestart, Math.min(pagestart+10, list.size()));
    }

    @RequestMapping(value="{id}", method=RequestMethod.GET)
    public ResponseEntity<ArticleModel> getArticleById(@PathVariable("id") String id) {
        ArticleModel article = articleService.getById(id);
        if(article == null) {
            return new ResponseEntity<ArticleModel>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<ArticleModel>(article, HttpStatus.OK);
        }
    }

    @RequestMapping(value="/tags", method=RequestMethod.GET)
    public List<String> getDistinctByTag() {
        return articleService.getDistinctTags();
    }

    @RequestMapping(value="/languages", method=RequestMethod.GET)
    public List<String> getDistinctByLanguage() {
        return articleService.getDistinctLanguages();
    }

    @RequestMapping(value="{id}", method=RequestMethod.PUT)
    public ResponseEntity<ArticleModel> updateArticle(@Valid @RequestBody ArticleModel article, @PathVariable("id") String id) {
        ArticleModel articleData = articleService.getById(id);
        if(articleData == null) {
            return new ResponseEntity<ArticleModel>(HttpStatus.NOT_FOUND);
        }
        articleData.setTitle(article.getTitle());
        articleData.setContent(article.getContent());
        articleData.setImageUrl(article.getImageUrl());
        articleData.setTag(article.getTag());
        articleData.setSourceTags(article.getSourceTags());
        articleData.setSource(article.getSource());
        articleData.setVideoUrl(article.getVideoUrl());
        articleData.setLanguage(article.getLanguage());
        articleData.setImageHeight(article.getImageHeight());
        articleData.setImageWidth(article.getImageWidth());
        articleData.setSummary(article.getSummary());
        articleData.setLanguage(article.getLanguage());
        ArticleModel updatedArticle = articleService.save(articleData);

        return new ResponseEntity<ArticleModel>(updatedArticle, HttpStatus.OK);
    }

    @RequestMapping(value="{id}", method=RequestMethod.DELETE)
    public void deleteArticle(@PathVariable("id") String id) {
        articleService.delete(id);
    }
    
}
