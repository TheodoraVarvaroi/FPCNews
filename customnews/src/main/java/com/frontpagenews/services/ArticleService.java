package com.frontpagenews.services;

import com.frontpagenews.models.ArticleModel;

import java.util.List;

public interface ArticleService extends CrudService<ArticleModel>{
    public ArticleModel getOneByTags (List<String> tags);
}
