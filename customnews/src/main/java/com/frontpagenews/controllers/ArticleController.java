package com.frontpagenews.controllers;

import com.frontpagenews.models.ArticleModel;
import com.frontpagenews.services.ArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/articles")
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
    public List<ArticleModel> getArticlesOnPage(@PathVariable("page") String page) {
        if(page.equals("0"))
            return null;
        int pagestart = (Integer.parseInt(page) - 1) * 10;
        List<ArticleModel> list = articleService.getAllSorted().subList(pagestart, pagestart+10);
        return list;
    }

    @RequestMapping(value="/tags/{tags}", method=RequestMethod.GET)
    public List<ArticleModel> getArticlesWithTags(@PathVariable("tags") String tags) {
        List<String> tagsList = Arrays.asList(tags.split("&"));
        return articleService.getByTagInSorted(tagsList);
    }

    @RequestMapping(value="/tags/{tags}/page/{page}", method=RequestMethod.GET)
    public List<ArticleModel> getArticlesWithTagsOnPage(@PathVariable("page") String page, @PathVariable("tags") String tags) {
        List<String> tagsList = Arrays.asList(tags.split("&"));
        if(page.equals("0"))
            return null;
        int pagestart = (Integer.parseInt(page) - 1) * 10;
        List<ArticleModel> list = articleService.getByTagInSorted(tagsList).subList(pagestart, pagestart+10);
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

    @RequestMapping(value="/tags", method=RequestMethod.GET)
    public List<String> getDistinctByTag() {
        return articleService.getDistinctTags();
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
        ArticleModel updatedArticle = articleService.save(articleData);
        return new ResponseEntity<ArticleModel>(updatedArticle, HttpStatus.OK);
    }

    @RequestMapping(value="{id}", method=RequestMethod.DELETE)
    public void deleteArticle(@PathVariable("id") String id) {
        articleService.delete(id);
    }
    
}
