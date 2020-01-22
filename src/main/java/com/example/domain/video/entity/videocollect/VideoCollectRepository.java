package com.example.domain.video.entity.videocollect;


import java.util.List;
import java.util.Optional;

public interface VideoCollectRepository {

    VideoCollect add(VideoCollect video);

    void deleteByVideoAndUser(Integer videoId, Integer userId);

    Optional<VideoCollect> getByVideoAndUser(Integer videoId, Integer userId);

    List<VideoCollect> listByUser(Integer userId);
}
