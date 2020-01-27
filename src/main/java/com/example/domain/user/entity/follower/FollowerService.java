package com.example.domain.user.entity.follower;

import com.example.domain.execption.BusinessException;
import com.example.domain.user.User;
import com.example.domain.user.UserService;
import com.example.infrastructure.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FollowerService {

    @Autowired
    private FollowerRepository followerRepository;

    @Autowired
    private UserService userService;

    public Follower follow(Follower follower) {
        if (followerRepository.getByUserAndFollower(follower.getUserId(), follower.getFollowerId()).isPresent()) {
            throw new BusinessException("已关注该用户");
        }

        follower.setFollowTime(DataUtils.getCurrentDataTime());
        followerRepository.add(follower);

        User user = userService.getById(follower.getUserId());
        userService.increaseFanCount(user);

        User followerUser = userService.getById(follower.getFollowerId());
        userService.increaseFollowCount(followerUser);

        return follower;
    }

    public void unFollow(Integer userId, Integer followerId) {
        followerRepository.deleteByUserAndFollower(userId, followerId);

        User user = userService.getById(userId);
        userService.decreaseFanCount(user);

        User followerUser = userService.getById(followerId);
        userService.decreaseFollowCount(followerUser);
    }

    public List<Follower> listByFollower(Integer followerId) {
        return followerRepository.listByFollower(followerId);
    }

    public List<Follower> listByUser(Integer userId) {
        return followerRepository.listByUser(userId);
    }

}
