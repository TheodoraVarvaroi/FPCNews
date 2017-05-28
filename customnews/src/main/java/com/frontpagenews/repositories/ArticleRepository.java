package com.frontpagenews.repositories;

import com.frontpagenews.models.ArticleModel;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends MongoRepository<ArticleModel, String>{
    public List<ArticleModel> findAll();
    public List<ArticleModel> findAll(Sort sort);
    public ArticleModel findOne(String id);
    public ArticleModel findByTitle(String title);
    public List<ArticleModel> findByLanguage(String language);
    public List<ArticleModel> findByLanguage(String language, Sort sort);
    public List<ArticleModel> findByLanguageAndTagIn(String language,List<String> tags, Sort sort);
    public List<ArticleModel> findByTagIn(List<String> tags);
    public List<ArticleModel> findByTagIn(List<String> tags, Sort sort);
    public ArticleModel findByTag(String tag);
    public ArticleModel findByTag(String tag, Sort sort);
    public ArticleModel save(ArticleModel article);
    public void delete(String id);
    public long count();
    boolean exists(String id);
}
