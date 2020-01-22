package com.example.app.video;

import com.example.app.user.FollowerApp;
import com.example.controller.common.Operator;
import com.example.domain.execption.UnauthorizedException;
import com.example.domain.user.entity.follower.Follower;
import com.example.domain.video.Video;
import com.example.domain.video.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VideoApp {

    @Autowired
    private VideoService videoService;

    @Autowired
    private FollowerApp followerApp;

    @Autowired
    private Operator operator;

    @Transactional(rollbackFor = Exception.class)
    public Video add(Video video) {
        return videoService.add(video);
    }

    public Video getById(Integer id) {
        return videoService.getById(id);
    }

    public List<Video> listRandom(Integer size) {
        return videoService.listRandom(size);
    }

    public List<Video> listFollow(int videoNum) {
        if (operator.getId() == null || operator.getId() == 0) {
            throw new UnauthorizedException();
        }

        List<Follower> followers = followerApp.listByFollower(operator.getId());
        List<Video> videos = videoService.listInCreateBy(followers.stream()
                .map(follower -> follower.getUserId()).collect(Collectors.toList()));

        Collections.shuffle(videos);

        if (videos.size() > videoNum) {
            return videos.subList(0, videoNum);
        }

        return videos;
    }

    public List<Video> listInCreateBy(List<Integer> createBys) {
        return videoService.listInCreateBy(createBys);
    }
}
