package com.example.controller.article;

import com.example.app.article.ArticleApp;
import com.example.app.article.ArticleCollectApp;
import com.example.controller.common.Operator;
import com.example.controller.common.Result;
import com.example.controller.article.ArticleRequest.*;
import com.example.controller.video.VideoCollectRequest;
import com.example.domain.article.Article;
import com.example.domain.article.entity.articlecollect.ArticleCollect;
import com.example.domain.video.entity.videocollect.VideoCollect;
import com.example.infrastructure.utils.DataUtils;
import com.example.infrastructure.utils.UploadUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"文章接口"})
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleCollectApp articleCollectApp;

    @Autowired
    private ArticleApp articleApp;

    @Autowired
    private Operator operator;

    @ApiOperation(value = "创建")
    @PostMapping
    public Result add(CreateArticle request) {
        Article article = Article.builder().build();
        BeanUtils.copyProperties(request, article);
        article.setCreateBy(operator.getId());
        article.setCreateAt(DataUtils.getCurrentDataTime());
        article.setPictures(UploadUtils.uploadPicture(request.getPictures()));
        articleApp.add(article);
        return Result.builder()
                .data(article)
                .build();
    }

    @ApiOperation(value = "根据Id获取")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "文章Id", required = true, dataType = "Integer", paramType = "path")})
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return Result.builder()
                .data(articleApp.getById(id))
                .build();
    }

    @ApiOperation(value = "收藏文章")
    @PostMapping("/collect")
    public Result collect(@RequestBody ArticleCollectRequest.Collect request) {
        if ("0".equals(request.getType())) { // 取消收藏
            articleCollectApp.unCollect(request.getArticleId(), operator.getId());

            return Result.builder()
                    .msg("取消收藏成功")
                    .build();
        } else if ("1".equals(request.getType())) { // 关注
            ArticleCollect collect = ArticleCollect.builder()
                    .articleId(request.getArticleId())
                    .userId(operator.getId())
                    .build();
            articleCollectApp.collect(collect);

            return Result.builder()
                    .msg("收藏成功")
                    .data(collect)
                    .build();
        }

        return null;
    }

}
