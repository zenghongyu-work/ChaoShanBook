package com.example.controller.media;

import com.example.app.article.ArticleApp;
import com.example.app.article.ArticleCollectApp;
import com.example.app.video.VideoApp;
import com.example.app.video.VideoCollectApp;
import com.example.controller.assembler.MediaAssembler;
import com.example.controller.common.Operator;
import com.example.controller.common.Result;
import com.example.domain.article.Article;
import com.example.domain.article.entity.articlecollect.ArticleCollect;
import com.example.domain.execption.NotFoundException;
import com.example.domain.video.Video;
import com.example.domain.video.entity.videocollect.VideoCollect;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.domain.common.EnumType.MediaType.ARTICLE;
import static com.example.domain.common.EnumType.MediaType.VIDEO;

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

    @Autowired
    private VideoCollectApp videoCollectApp;

    @Autowired
    private ArticleCollectApp articleCollectApp;

    @Autowired
    private MediaAssembler mediaAssembler;

    @Autowired
    private Operator operator;

    @ApiOperation(value = "获取列表")
    @GetMapping
    public Result list(@RequestParam("type") String type,
                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        int videoNum = (int) (Math.random() * pageSize);
        int articleNum = pageSize - videoNum;

        List<Video> videos = null;
        List<Article> articles = null;

        if ("0".equals(type)) {
            videos = videoApp.listFollow(videoNum);
            articles = articleApp.listFollow(articleNum);
        } else if ("1".equals(type)) {
            videos = videoApp.listRandom(videoNum);
            articles = articleApp.listRandom(articleNum);
        }

        List<MediaResponse.MediaDetail> medias = new ArrayList<>(pageSize);
        medias.addAll(videos.stream().map(video -> {
            video.setVideo(videoBaseUrl + video.getVideo());
            video.setSnapshot(pictureBaseUrl + video.getSnapshot());
            return mediaAssembler.assemblerUser(MediaResponse.MediaDetail
                    .builder()
                    .type(VIDEO)
                    .media(video)
                    .isCollect(videoCollectApp.isCollect(video.getId(), operator.getId()) ? "1" : "0")
                    .build());
        }).collect(Collectors.toList()));

        medias.addAll(articles.stream().map(article -> {
            article.getPictures().stream().forEach(picture -> picture.setPath(pictureBaseUrl + picture.getPath()));
            return mediaAssembler.assemblerUser(MediaResponse.MediaDetail
                    .builder()
                    .type(ARTICLE)
                    .media(article)
                    .isCollect(articleCollectApp.isCollect(article.getId(), operator.getId()) ? "1" : "0")
                    .build());
        }).collect(Collectors.toList()));

        Collections.shuffle(medias);
        return Result.builder()
                .data(medias)
                .build();
    }

    @ApiOperation(value = "获取收藏列表")
    @GetMapping("/collect")
    public Result listCollect(@RequestParam(required = false) Integer id) {
        Integer actualId;
        if (id != null && id != 0) {
            actualId = id;
        } else {
            actualId = operator.getId();
        }

        List<VideoCollect> videoCollects = videoCollectApp.listByUser(actualId);
        List<ArticleCollect> articleCollects = articleCollectApp.listByUser(actualId);

        List<MediaResponse.MediaDetail> medias = new ArrayList<>();
        int i;
        int j;

        for (i = 0, j = 0; i < videoCollects.size() && j < articleCollects.size();) {
            VideoCollect videoCollect = videoCollects.get(i);
            ArticleCollect articleCollect = articleCollects.get(j);

            if (StringUtils.compare(videoCollect.getCollectTime(), articleCollect.getCollectTime()) > 0) {
                Video video;
                try {
                    video = videoApp.getById(videoCollect.getVideoId());
                } catch (NotFoundException e) {
                    videoCollectApp.unCollect(videoCollect.getVideoId(), actualId);
                    i++;
                    continue;
                }
                video.setVideo(videoBaseUrl + video.getVideo());
                video.setSnapshot(pictureBaseUrl + video.getSnapshot());
                medias.add(mediaAssembler.assemblerUser(MediaResponse.MediaDetail
                        .builder()
                        .type(VIDEO)
                        .media(video)
                        .isCollect("1")
                        .build()));
                i++;
            } else {
                Article article;
                try {
                    article = articleApp.getById(articleCollect.getArticleId());
                } catch (NotFoundException e) {
                    articleCollectApp.unCollect(articleCollect.getArticleId(), actualId);
                    j++;
                    continue;
                }

                article.getPictures().stream().forEach(picture -> picture.setPath(pictureBaseUrl + picture.getPath()));
                medias.add(mediaAssembler.assemblerUser(MediaResponse.MediaDetail
                        .builder()
                        .type(ARTICLE)
                        .media(article)
                        .isCollect("1")
                        .build()));
                j++;
            }
        }

        if (i < videoCollects.size()) {
            for (;i < videoCollects.size();i++) {
                VideoCollect videoCollect = videoCollects.get(i);
                Video video;
                try {
                    video = videoApp.getById(videoCollect.getVideoId());
                } catch (NotFoundException e) {
                    videoCollectApp.unCollect(videoCollect.getVideoId(), actualId);
                    continue;
                }
                video.setVideo(videoBaseUrl + video.getVideo());
                video.setSnapshot(pictureBaseUrl + video.getSnapshot());
                medias.add(mediaAssembler.assemblerUser(MediaResponse.MediaDetail
                        .builder()
                        .type(VIDEO)
                        .media(video)
                        .isCollect("1")
                        .build()));
            }
        }

        if (j < articleCollects.size()) {
            for (;j < articleCollects.size();j++) {
                ArticleCollect articleCollect = articleCollects.get(j);
                Article article;
                try {
                    article = articleApp.getById(articleCollect.getArticleId());
                } catch (NotFoundException e) {
                    articleCollectApp.unCollect(articleCollect.getArticleId(), actualId);
                    continue;
                }
                article.getPictures().stream().forEach(picture -> picture.setPath(pictureBaseUrl + picture.getPath()));
                medias.add(mediaAssembler.assemblerUser(MediaResponse.MediaDetail
                        .builder()
                        .type(ARTICLE)
                        .media(article)
                        .isCollect("1")
                        .build()));
            }
        }

        return Result.builder()
                .data(medias)
                .build();
    }

    @ApiOperation(value = "获取我的创建列表")
    @GetMapping("/create")
    public Result listCreate(@RequestParam(required = false) Integer id) {
        Integer actualId;
        if (id != null && id != 0) {
            actualId = id;
        } else {
            actualId = operator.getId();
        }

        List<Video> videos = videoApp.listInCreateBy(Arrays.asList(actualId));
        List<Article> articles = articleApp.listInCreateBy(Arrays.asList(actualId));

        List<MediaResponse.MediaDetail> medias = new ArrayList<>();
        int i;
        int j;

        for (i = 0, j = 0; i < videos.size() && j < articles.size();) {
            Video video = videos.get(i);
            Article article = articles.get(j);

            if (StringUtils.compare(video.getCreateAt(), article.getCreateAt()) > 0) {
                video.setVideo(videoBaseUrl + video.getVideo());
                video.setSnapshot(pictureBaseUrl + video.getSnapshot());
                medias.add(mediaAssembler.assemblerUser(MediaResponse.MediaDetail
                        .builder()
                        .type(VIDEO)
                        .media(video)
                        .isCollect(videoCollectApp.isCollect(video.getId(), actualId) ? "1" : "0")
                        .build()));
                i++;
            } else {
                article.getPictures().stream().forEach(picture -> picture.setPath(pictureBaseUrl + picture.getPath()));
                medias.add(mediaAssembler.assemblerUser(MediaResponse.MediaDetail
                        .builder()
                        .type(ARTICLE)
                        .media(article)
                        .isCollect(articleCollectApp.isCollect(article.getId(), actualId) ? "1" : "0")
                        .build()));
                j++;
            }
        }

        if (i < videos.size()) {
            for (;i < videos.size();i++) {
                Video video = videos.get(i);
                video.setVideo(videoBaseUrl + video.getVideo());
                video.setSnapshot(pictureBaseUrl + video.getSnapshot());
                medias.add(mediaAssembler.assemblerUser(MediaResponse.MediaDetail
                        .builder()
                        .type(VIDEO)
                        .media(video)
                        .isCollect(videoCollectApp.isCollect(video.getId(), actualId) ? "1" : "0")
                        .build()));
            }
        }

        if (j < articles.size()) {
            for (;j < articles.size();j++) {
                Article article = articles.get(j);
                article.getPictures().stream().forEach(picture -> picture.setPath(pictureBaseUrl + picture.getPath()));
                medias.add(mediaAssembler.assemblerUser(MediaResponse.MediaDetail
                        .builder()
                        .type(ARTICLE)
                        .media(article)
                        .isCollect(articleCollectApp.isCollect(article.getId(), actualId) ? "1" : "0")
                        .build()));
            }
        }

        return Result.builder()
                .data(medias)
                .build();
    }
}
