package com.example.domain.article.entity.articlecollect;

import com.example.domain.execption.BusinessException;
import com.example.infrastructure.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ArticleCollectService {

    @Autowired
    private ArticleCollectRepository articleCollectRepository;

    public ArticleCollect collect(ArticleCollect collect) {
        if (articleCollectRepository.getByArticleAndUser(collect.getArticleId(), collect.getUserId()).isPresent()) {
            throw new BusinessException("已收藏该文章");
        }

        collect.setCollectTime(DataUtils.getCurrentDataTime());
        return articleCollectRepository.add(collect);
    }

    public void unCollect(Integer articleId, Integer userId) {
        articleCollectRepository.deleteByArticleAndUser(articleId, userId);
    }

    public List<ArticleCollect> listByUser(Integer userId) {
        return articleCollectRepository.listByUser(userId);
    }
}
