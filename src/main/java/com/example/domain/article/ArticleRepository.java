package com.example.domain.article;


import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Article add(Article article);

    Optional<Article> getById(Integer id);

    List<Article> listRandom(Integer size);
}
