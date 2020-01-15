package com.example.controller.article;

import com.example.app.ArticleApp;
import com.example.controller.common.Operator;
import com.example.controller.common.Result;
import com.example.controller.article.ArticleRequest.*;
import com.example.domain.article.Article;
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

}
