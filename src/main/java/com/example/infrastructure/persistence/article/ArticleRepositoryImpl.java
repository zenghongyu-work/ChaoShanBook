package com.example.infrastructure.persistence.article;

import com.example.domain.article.Article;
import com.example.domain.article.ArticleRepository;
import com.example.domain.article.valueobject.Picture;
import com.example.infrastructure.persistence.article.valueobject.PictureDbo;
import com.example.infrastructure.persistence.article.valueobject.PictureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private PictureMapper pictureMapper;

    @Override
    public Article add(Article article) {
        ArticleDbo dbo = ArticleDbo.fromModule(article, ArticleDbo.class);
        articleMapper.insert(dbo);
        article.setId(dbo.getId());
        refreshPictures(article);
        return article;
    }

    public void refreshPictures(Article article) {
        Example example = new Example(PictureDbo.class);
        example.createCriteria().andEqualTo("articleId", article.getId());
        pictureMapper.deleteByExample(example);

        article.getPictures().stream().forEach(picture ->
                pictureMapper.insert(PictureDbo.builder()
                .articleId(article.getId())
                .path(picture.getPath())
                .build()));
    }

    public List<Picture> getPictures(Article article) {
        Example example = new Example(PictureDbo.class);
        example.createCriteria().andEqualTo("articleId", article.getId());
        List<PictureDbo> pictureDbos =  pictureMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(pictureDbos)) {
            return Collections.emptyList();
        } else {
            return pictureDbos.stream()
                    .map(pictureDbo -> pictureDbo.toModule(Picture.class))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Optional<Article> getById(Integer id) {
        Example example = new Example(ArticleDbo.class);
        example.createCriteria().andEqualTo("id", id);
        ArticleDbo articleDbo = articleMapper.selectOneByExample(example);
        if (articleDbo == null) {
            return Optional.empty();
        } else {
            Article article = articleDbo.toModule(Article.class);
            article.setPictures(getPictures(article));
            return Optional.of(article);
        }
    }

    @Override
    public List<Article> listRandom(Integer size) {
        List<ArticleDbo> articleDbos = articleMapper.listRandom(size);
        if (CollectionUtils.isEmpty(articleDbos)) {
            return Collections.emptyList();
        } else {
            return articleDbos.stream()
                    .map(articleDbo -> {
                        Article article = articleDbo.toModule(Article.class);
                        article.setPictures(getPictures(article));
                        return article;
                    }).collect(Collectors.toList());
        }
    }

}
