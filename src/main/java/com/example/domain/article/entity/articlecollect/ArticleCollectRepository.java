package com.example.domain.article.entity.articlecollect;


import java.util.List;
import java.util.Optional;

public interface ArticleCollectRepository {

    ArticleCollect add(ArticleCollect collect);

    Optional<ArticleCollect> getByArticleAndUser(Integer articleId, Integer userId);

    void deleteByArticleAndUser(Integer articleId, Integer userId);

    List<ArticleCollect> listByUser(Integer userId);
}
