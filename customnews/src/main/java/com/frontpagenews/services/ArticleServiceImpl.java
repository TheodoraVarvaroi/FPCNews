package com.frontpagenews.services;

import com.frontpagenews.models.ArticleModel;
import com.frontpagenews.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository repository;
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public ArticleModel save(ArticleModel entity) {
        return this.repository.save(entity);
    }

    @Override
    public List<ArticleModel> getAll() {
        return this.repository.findAll();
    }

    @Override
    public ArticleModel getById(String id) {
        return this.repository.findOne(id);
    }

    @Override
    public void delete(String id) {
        this.repository.delete(id);
    }

    public ArticleModel getOneByTag(String tag) {
        return this.repository.findByTag(tag);
    }

    public List<String> getDistinctTags() {
        return mongoTemplate.getCollection("articles").distinct("tag");
    }

    public List<ArticleModel> getByTagIn(List<String> tags) {
        return this.repository.findByTagIn(tags);
    }
};