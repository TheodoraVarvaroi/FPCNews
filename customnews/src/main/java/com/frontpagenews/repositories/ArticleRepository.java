package com.frontpagenews.repositories;

import com.frontpagenews.models.ArticleModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends MongoRepository<ArticleModel, String>{
    public List<ArticleModel> findAll();
    public ArticleModel findOne(String id);
    public ArticleModel findByTitle(String title);
    public ArticleModel findByTags(List<String> tags);
    public ArticleModel save(ArticleModel article);
    public void delete(String id);
    public long count();
    boolean exists(String id);
}
