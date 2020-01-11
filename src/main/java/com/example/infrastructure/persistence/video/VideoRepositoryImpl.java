package com.example.infrastructure.persistence.video;

import com.example.domain.video.Video;
import com.example.domain.video.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class VideoRepositoryImpl implements VideoRepository {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public Video add(Video video) {
        VideoDbo dbo = VideoDbo.fromModule(video, VideoDbo.class);
        videoMapper.insert(dbo);
        video.setId(dbo.getId());
        return video;
    }

    @Override
    public Optional<Video> getById(Integer id) {
        Example example = new Example(VideoDbo.class);
        example.createCriteria().andEqualTo("id", id);
        VideoDbo userDbo = videoMapper.selectOneByExample(example);
        if (userDbo == null) {
            return Optional.empty();
        } else {
            return Optional.of(videoMapper.selectOneByExample(example).toModule(Video.class));
        }
    }

    @Override
    public List<Video> list() {
        Example example = new Example(VideoDbo.class);
        example.setOrderByClause("create_at desc");
        List<VideoDbo> videoDbos = videoMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(videoDbos)) {
            return Collections.EMPTY_LIST;
        } else {
            return videoDbos.stream()
                    .map(videoDbo -> videoDbo.toModule(Video.class))
                    .collect(Collectors.toList());
        }
    }

}
