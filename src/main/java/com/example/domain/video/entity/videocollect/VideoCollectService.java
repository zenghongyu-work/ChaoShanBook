package com.example.domain.video.entity.videocollect;

import com.example.domain.execption.BusinessException;
import com.example.infrastructure.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VideoCollectService {

    @Autowired
    private VideoCollectRepository videoCollectRepository;

    public VideoCollect collect(VideoCollect collect) {
        if (videoCollectRepository.getByVideoAndUser(collect.getVideoId(), collect.getUserId()).isPresent()) {
            throw new BusinessException("已收藏该视频");
        }

        collect.setCollectTime(DataUtils.getCurrentDataTime());
        return videoCollectRepository.add(collect);
    }

    public void unCollect(Integer videoId, Integer userId) {
        videoCollectRepository.deleteByVideoAndUser(videoId, userId);
    }

    public List<VideoCollect> listByUser(Integer userId) {
        return videoCollectRepository.listByUser(userId);
    }
}
