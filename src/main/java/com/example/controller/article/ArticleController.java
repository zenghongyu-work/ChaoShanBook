package com.example.controller.article;

import com.example.app.ArticleApp;
import com.example.controller.common.Operator;
import com.example.controller.common.Result;
import com.example.controller.article.ArticleRequest.*;
import com.example.domain.article.Article;
import com.example.domain.article.valueobject.Picture;
import com.example.domain.execption.BusinessException;
import com.example.infrastructure.utils.DataUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.example.domain.common.Constant.PICTURE_FORMATS;

@Api(tags = {"文章接口"})
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Value("${upload-file-path.picture}")
    private String pictureFilePath;

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
        article.setPictures(uploadPicture(request.getPictures()));
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

    @ApiOperation(value = "获取列表")
    @GetMapping
    public Result list() {
        return Result.builder()
                .data(articleApp.list())
                .build();
    }

    public List<Picture> uploadPicture(MultipartFile[] file) {
        List<Picture> pictures = new ArrayList<>();
        if (!ArrayUtils.isEmpty(file)) {
            Arrays.stream(file).forEach(multipartFile -> {
                String fileExt = multipartFile.getOriginalFilename()
                        .substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1)
                        .toLowerCase();
                if (!PICTURE_FORMATS.contains(fileExt)) {
                    throw new BusinessException(String.format("占不只支持%s格式图片上传", fileExt));
                }
                // 重构文件名称
                String pikId = UUID.randomUUID().toString().replaceAll("-", "");
                String newPictureName = pikId + "." + fileExt;
                File fileSave = new File(pictureFilePath, newPictureName);
                try {
                    multipartFile.transferTo(fileSave);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new BusinessException(String.format("上传图片失败：%s", e.getMessage()));
                }

                pictures.add(Picture.builder().path(newPictureName).build());
            });
        }

        return pictures;
    }
}
