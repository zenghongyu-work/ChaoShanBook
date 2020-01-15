package com.example.controller.resource;

import com.example.app.ArticleApp;
import com.example.app.VideoApp;
import com.example.controller.common.Operator;
import com.example.controller.common.Result;
import com.example.domain.article.Article;
import com.example.domain.video.Video;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"资源接口"})
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private VideoApp videoApp;

    @Autowired
    private ArticleApp articleApp;

    @ApiOperation(value = "获取列表")
    @GetMapping
    public Result list(@RequestParam("type") String type,
                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        int videoNum = (int) (Math.random() * pageSize);
        int articleNum = pageSize - videoNum;
        List<Video> videos = videoApp.listRandom(videoNum);
        List<Article> articles = articleApp.listRandom(articleNum);

        List<ResourceResponse.Resource> resources = new ArrayList<>(pageSize);
        resources.addAll(videos.stream().map(video -> {
            video.setVideo("http://111.231.245.190:8080/chaoshanbook/upload/video/" + video.getVideo());
            return ResourceResponse.Resource
                    .builder()
                    .type("0")
                    .video(video).build();
        }).collect(Collectors.toList()));

        resources.addAll(articles.stream().map(article -> {
            article.getPictures().stream().forEach(picture -> picture.setPath("http://111.231.245.190:8080/chaoshanbook/upload/picture/" + picture.getPath()));
            return ResourceResponse.Resource
                    .builder()
                    .type("1")
                    .article(article).build();
        }).collect(Collectors.toList()));

        Collections.shuffle(resources);
        return Result.builder()
                .data(resources)
                .build();
    }


}
