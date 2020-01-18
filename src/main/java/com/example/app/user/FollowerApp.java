package com.example.app.user;

import com.example.domain.user.entity.follower.Follower;
import com.example.domain.user.entity.follower.FollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class FollowerApp {

    @Autowired
    private FollowerService followerService;

    @Transactional(rollbackFor = Exception.class)
    public Follower follow(Follower follower) {
        return followerService.follow(follower);
    }

    @Transactional(rollbackFor = Exception.class)
    public void unFollow(Integer userId, Integer followerId) {
        followerService.unFollow(userId, followerId);
    }

    public List<Follower> listByFollower(Integer followerId) {
        return followerService.listByFollower(followerId);
    }

    public List<Follower> listByUser(Integer userId) {
        return followerService.listByUser(userId);
    }
}
