package com.example.app;

import com.example.app.user.FollowerApp;
import com.example.controller.common.Operator;
import com.example.domain.article.Article;
import com.example.domain.article.ArticleService;
import com.example.domain.execption.UnauthorizedException;
import com.example.domain.user.entity.follower.Follower;
import com.example.domain.video.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArticleApp {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private FollowerApp followerApp;

    @Autowired
    private Operator operator;

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

    public List<Article> listFollow(int articleNum) {
        if (operator.getId() == null || operator.getId() == 0) {
            throw new UnauthorizedException();
        }

        List<Follower> followers = followerApp.listByFollower(operator.getId());
        List<Article> articles = articleService.listInCreateBy(followers.stream()
                .map(follower -> follower.getUserId()).collect(Collectors.toList()));

        Collections.shuffle(articles);

        if (articles.size() > articleNum) {
            return articles.subList(0, articleNum);
        }

        return articles;
    }
}
