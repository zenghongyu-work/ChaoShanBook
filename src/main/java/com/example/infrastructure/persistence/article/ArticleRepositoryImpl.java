package com.example.infrastructure.persistence.article;

import com.example.domain.article.Article;
import com.example.domain.article.ArticleRepository;
import com.example.domain.article.valueobject.Picture;
import com.example.domain.video.Video;
import com.example.domain.video.VideoRepository;
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
        refreshPicture(article);
        return article;
    }

    public void refreshPicture(Article article) {
        article.getPictures().stream()
                .map(picture -> PictureDbo.builder()
                        .articleId(article.getId())
                        .path(picture.getPath())
                        .build()).collect(Collectors.toList());
    }

    @Override
    public Optional<Article> getById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Article> list() {
        return null;
    }

//    @Override
//    public Optional<Video> getById(Integer id) {
//        Example example = new Example(PictureDbo.class);
//        example.createCriteria().andEqualTo("id", id);
//        PictureDbo userDbo = articleMapper.selectOneByExample(example);
//        if (userDbo == null) {
//            return Optional.empty();
//        } else {
//            return Optional.of(articleMapper.selectOneByExample(example).toModule(Video.class));
//        }
//    }

//    @Override
//    public List<Video> list() {
//        Example example = new Example(PictureDbo.class);
//        example.setOrderByClause("create_at desc");
//        List<PictureDbo> articleDbos = articleMapper.selectByExample(example);
//        if (CollectionUtils.isEmpty(articleDbos)) {
//            return Collections.EMPTY_LIST;
//        } else {
//            return articleDbos.stream()
//                    .map(articleDbo -> articleDbo.toModule(Video.class))
//                    .collect(Collectors.toList());
//        }
//    }

}
