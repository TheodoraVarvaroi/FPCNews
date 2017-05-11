package com.frontpagenews.controllers;

import com.frontpagenews.models.ArticleModel;
import com.frontpagenews.models.SourceModel;
import com.frontpagenews.services.ArticleService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @RequestMapping(method= RequestMethod.GET)
    public List<ArticleModel> getAllArticles() {
        return articleService.getAll();
    }

    @RequestMapping(method=RequestMethod.POST)
    public ArticleModel createArticle(@Valid @RequestBody ArticleModel admin) {
        return articleService.save(admin);
    }

    @RequestMapping(value="/page/{page}", method=RequestMethod.GET)
    public List<ArticleModel> getArticlePage(@PathVariable("page") String page) {
        if(page.equals("0"))
            return null;
        int pagestart = (Integer.parseInt(page) - 1) * 10;
        List<ArticleModel> list = articleService.getAll().subList(pagestart, pagestart+10);

        return list;
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

    @RequestMapping(value="{id}", method=RequestMethod.PUT)
    public ResponseEntity<ArticleModel> updateArticle(@Valid @RequestBody ArticleModel article, @PathVariable("id") String id) {
        ArticleModel articleData = articleService.getById(id);
        if(articleData == null) {
            return new ResponseEntity<ArticleModel>(HttpStatus.NOT_FOUND);
        }
        articleData.setTitle(article.getTitle());
        articleData.setContent(article.getContent());
        articleData.setImageUrl(article.getImageUrl());
        articleData.setTags(article.getTags());
        articleData.setSource(article.getSource());
        articleData.setVideoUrl(article.getVideoUrl());
        ArticleModel updatedArticle = articleService.save(articleData);
        return new ResponseEntity<ArticleModel>(updatedArticle, HttpStatus.OK);
    }

    @RequestMapping(value="{id}", method=RequestMethod.DELETE)
    public void deleteArticle(@PathVariable("id") String id) {
        articleService.delete(id);
    }
    
}
