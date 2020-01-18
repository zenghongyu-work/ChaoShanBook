package com.example.infrastructure.persistence.user.entity.follower;

import com.example.domain.user.entity.follower.Follower;
import com.example.domain.user.entity.follower.FollowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class FollowerRepositoryImpl implements FollowerRepository {

    @Autowired
    private FollowerMapper followerMapper;

    @Override
    public Follower add(Follower follower) {
        FollowerDbo dbo = FollowerDbo.fromModule(follower, FollowerDbo.class);
        followerMapper.insert(dbo);
        follower.setId(dbo.getId());
        return follower;
    }

    @Override
    public Optional<Follower> getByUserAndFollower(Integer userId, Integer followerId) {
        Example example = new Example(FollowerDbo.class);
        example.createCriteria()
                .andEqualTo("userId", userId)
                .andEqualTo("followerId", followerId);
        FollowerDbo userDbo = followerMapper.selectOneByExample(example);
        if (userDbo == null) {
            return Optional.empty();
        } else {
            return Optional.of(followerMapper.selectOneByExample(example).toModule(Follower.class));
        }
    }

    @Override
    public void deleteByUserAndFollower(Integer userId, Integer followerId) {
        Example example = new Example(FollowerDbo.class);
        example.createCriteria()
                .andEqualTo("userId", userId)
                .andEqualTo("followerId", followerId);

        followerMapper.deleteByExample(example);
    }

    @Override
    public List<Follower> listByFollower(Integer followerId) {
        Example example = new Example(FollowerDbo.class);
        example.createCriteria()
                .andEqualTo("followerId", followerId);

        return followerMapper.selectByExample(example)
                .stream().map(followerDbo -> followerDbo.toModule(Follower.class)).collect(Collectors.toList());
    }

    @Override
    public List<Follower> listByUser(Integer userId) {
        Example example = new Example(FollowerDbo.class);
        example.createCriteria()
                .andEqualTo("userId", userId);

        return followerMapper.selectByExample(example)
                .stream().map(followerDbo -> followerDbo.toModule(Follower.class)).collect(Collectors.toList());
    }

}
