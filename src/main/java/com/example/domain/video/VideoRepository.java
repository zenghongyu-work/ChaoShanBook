package com.example.domain.video;


import java.util.List;
import java.util.Optional;

public interface VideoRepository {

    Video add(Video video);

    Optional<Video> getById(Integer id);

    List<Video> listRandom(Integer size);
}
