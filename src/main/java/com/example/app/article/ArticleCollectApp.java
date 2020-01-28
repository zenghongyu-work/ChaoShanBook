package com.example.app.article;

import com.example.domain.article.entity.articlecollect.ArticleCollect;
import com.example.domain.article.entity.articlecollect.ArticleCollectService;
import com.example.domain.video.entity.videocollect.VideoCollect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ArticleCollectApp {

    @Autowired
    private ArticleCollectService articleCollectService;

    @Transactional(rollbackFor = Exception.class)
    public ArticleCollect collect(ArticleCollect collect) {
        return articleCollectService.collect(collect);
    }

    @Transactional(rollbackFor = Exception.class)
    public void unCollect(Integer articleId, Integer userId) {
        articleCollectService.unCollect(articleId, userId);
    }

    public List<ArticleCollect> listByUser(Integer userId) {
        return articleCollectService.listByUser(userId);
    }

    public boolean isCollect(Integer articleId, Integer userId) {
        return articleCollectService.isCollect(articleId, userId);
    }
}
