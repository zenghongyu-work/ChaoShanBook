package com.example.app.video;

import com.example.domain.video.entity.videocollect.VideoCollect;
import com.example.domain.video.entity.videocollect.VideoCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class VideoCollectApp {

    @Autowired
    private VideoCollectService videoCollectService;

    @Transactional(rollbackFor = Exception.class)
    public VideoCollect collect(VideoCollect collect) {
        return videoCollectService.collect(collect);
    }

    @Transactional(rollbackFor = Exception.class)
    public void unCollect(Integer videoId, Integer userId) {
        videoCollectService.unCollect(videoId, userId);
    }

    public List<VideoCollect> listByUser(Integer userId) {
        return videoCollectService.listByUser(userId);
    }
}
