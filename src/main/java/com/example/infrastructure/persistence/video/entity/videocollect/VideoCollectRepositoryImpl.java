package com.example.infrastructure.persistence.video.entity.videocollect;

import com.example.domain.user.entity.follower.Follower;
import com.example.domain.video.entity.videocollect.VideoCollect;
import com.example.domain.video.entity.videocollect.VideoCollectRepository;
import com.example.infrastructure.persistence.user.entity.follower.FollowerDbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class VideoCollectRepositoryImpl implements VideoCollectRepository {

    @Autowired
    private VideoCollectMapper videoCollectMapper;

    @Override
    public VideoCollect add(VideoCollect collect) {
        VideoCollectDbo dbo = VideoCollectDbo.fromModule(collect, VideoCollectDbo.class);
        videoCollectMapper.insert(dbo);
        collect.setId(dbo.getId());
        return collect;
    }

    @Override
    public void deleteByVideoAndUser(Integer videoId, Integer userId) {
        Example example = new Example(VideoCollectDbo.class);
        example.createCriteria()
                .andEqualTo("videoId", videoId)
                .andEqualTo("userId", userId);

        videoCollectMapper.deleteByExample(example);
    }

    @Override
    public Optional<VideoCollect> getByVideoAndUser(Integer videoId, Integer userId) {
        Example example = new Example(VideoCollectDbo.class);
        example.createCriteria()
                .andEqualTo("videoId", videoId)
                .andEqualTo("userId", userId);
        VideoCollectDbo dbo = videoCollectMapper.selectOneByExample(example);
        if (dbo == null) {
            return Optional.empty();
        } else {
            return Optional.of(dbo.toModule(VideoCollect.class));
        }
    }

    @Override
    public List<VideoCollect> listByUser(Integer userId) {
        Example example = new Example(VideoCollectDbo.class);
        example.createCriteria()
                .andEqualTo("userId", userId);
        example.setOrderByClause("collect_time desc");

        return videoCollectMapper.selectByExample(example)
                .stream().map(videoCollectDbo -> videoCollectDbo.toModule(VideoCollect.class)).collect(Collectors.toList());
    }
}
