package com.example.domain.video;

import com.example.domain.execption.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    public Video add(Video video) {
        return videoRepository.add(video);
    }

    public Video getById(Integer id) {
        return videoRepository.getById(id).orElseThrow(() -> new NotFoundException(String.format("短视频不存在（%d）", id)));
    }

    public List<Video> list() {
        return videoRepository.list();
    }
}
