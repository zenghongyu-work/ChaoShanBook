package com.example.controller.media;

import com.example.app.ArticleApp;
import com.example.app.VideoApp;
import com.example.controller.common.Result;
import com.example.domain.article.Article;
import com.example.domain.video.Video;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"媒体接口"})
@RestController
@RequestMapping("/media")
public class MediaController {

    @Value("${video-base-url}")
    private String videoBaseUrl;

    @Value("${picture-base-url}")
    private String pictureBaseUrl;

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

        List<MediaResponse.Media> medias = new ArrayList<>(pageSize);
        medias.addAll(videos.stream().map(video -> {
            video.setVideo(videoBaseUrl + video.getVideo());
            return MediaResponse.Media
                    .builder()
                    .type("0")
                    .media(video).build();
        }).collect(Collectors.toList()));

        medias.addAll(articles.stream().map(article -> {
            article.getPictures().stream().forEach(picture -> picture.setPath(pictureBaseUrl + picture.getPath()));
            return MediaResponse.Media
                    .builder()
                    .type("1")
                    .media(article).build();
        }).collect(Collectors.toList()));

        Collections.shuffle(medias);
        return Result.builder()
                .data(medias)
                .build();
    }


}
