package com.example.domain.article;

import com.example.domain.execption.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public Article add(Article article) {
        return articleRepository.add(article);
    }

    public Article getById(Integer id) {
        return articleRepository.getById(id).orElseThrow(() -> new BusinessException(String.format("文章不存在（%d）", id)));
    }

    public List<Article> list() {
        return articleRepository.list();
    }
}
