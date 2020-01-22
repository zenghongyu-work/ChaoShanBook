package com.example.infrastructure.persistence.article.entity.videocollect;

import com.example.domain.article.entity.articlecollect.ArticleCollect;
import com.example.domain.article.entity.articlecollect.ArticleCollectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ArticleCollectRepositoryImpl implements ArticleCollectRepository {

    @Autowired
    private ArticleCollectMapper articleCollectMapper;

    @Override
    public ArticleCollect add(ArticleCollect collect) {
        ArticleCollectDbo dbo = ArticleCollectDbo.fromModule(collect, ArticleCollectDbo.class);
        articleCollectMapper.insert(dbo);
        collect.setId(dbo.getId());
        return collect;
    }

    @Override
    public Optional<ArticleCollect> getByArticleAndUser(Integer articleId, Integer userId) {
        Example example = new Example(ArticleCollectDbo.class);
        example.createCriteria()
                .andEqualTo("articleId", articleId)
                .andEqualTo("userId", userId);
        ArticleCollectDbo dbo = articleCollectMapper.selectOneByExample(example);
        if (dbo == null) {
            return Optional.empty();
        } else {
            return Optional.of(dbo.toModule(ArticleCollect.class));
        }
    }

    @Override
    public void deleteByArticleAndUser(Integer articleId, Integer userId) {
        Example example = new Example(ArticleCollectDbo.class);
        example.createCriteria()
                .andEqualTo("articleId", articleId)
                .andEqualTo("userId", userId);

        articleCollectMapper.deleteByExample(example);
    }

    @Override
    public List<ArticleCollect> listByUser(Integer userId) {
        Example example = new Example(ArticleCollectDbo.class);
        example.createCriteria()
                .andEqualTo("userId", userId);
        example.setOrderByClause("collect_time desc");

        return articleCollectMapper.selectByExample(example)
                .stream().map(videoCollectDbo -> videoCollectDbo.toModule(ArticleCollect.class)).collect(Collectors.toList());
    }
}
