package com.example.app;

import com.example.domain.article.Article;
import com.example.domain.article.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ArticleApp {

    @Autowired
    private ArticleService articleService;

    @Transactional(rollbackFor = Exception.class)
    public Article add(Article article) {
        return articleService.add(article);
    }

    public Article getById(Integer id) {
        return articleService.getById(id);
    }

    public List<Article> listRandom(Integer size) {
        return articleService.listRandom(size);
    }
}
