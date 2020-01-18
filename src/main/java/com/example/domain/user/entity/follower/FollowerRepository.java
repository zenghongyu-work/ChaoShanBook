package com.example.domain.user.entity.follower;


import java.util.List;
import java.util.Optional;

public interface FollowerRepository {

    Follower add(Follower follower);

    Optional<Follower> getByUserAndFollower(Integer userId, Integer followerId);

    void deleteByUserAndFollower(Integer userId, Integer id);

    List<Follower> listByFollower(Integer followerId);

    List<Follower> listByUser(Integer userId);
}
