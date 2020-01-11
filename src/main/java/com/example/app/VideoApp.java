package com.example.app;

import com.example.domain.video.Video;
import com.example.domain.video.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class VideoApp {

    @Autowired
    private VideoService videoService;

    @Transactional(rollbackFor = Exception.class)
    public Video add(Video video) {
        return videoService.add(video);
    }

    public Video getById(Integer id) {
        return videoService.getById(id);
    }

    public List<Video> list() {
        return videoService.list();
    }
}
