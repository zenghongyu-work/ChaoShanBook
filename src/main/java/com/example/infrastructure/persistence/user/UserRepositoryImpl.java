package com.example.infrastructure.persistence.user;

import com.example.domain.user.User;
import com.example.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User add(User user) {
        UserDbo dbo = UserDbo.fromModule(user);
        userMapper.insert(dbo);
        user.setId(dbo.getId());
        return user;
    }

    @Override
    public User update(User user) {
        UserDbo dbo = UserDbo.fromModule(user);

        userMapper.updateByPrimaryKey(dbo);
        return user;
    }

    @Override
    public User updateToken(User user) {
        UserDbo dbo = UserDbo.builder()
                .id(user.getId())
                .token(user.getToken())
                .build();

        userMapper.updateByPrimaryKeySelective(dbo);
        return user;
    }

    @Override
    public Optional<User> getByName(String name) {
        Example example = new Example(UserDbo.class);
        example.createCriteria().andEqualTo("name", name);
        UserDbo userDbo = userMapper.selectOneByExample(example);
        if (userDbo == null) {
            return Optional.empty();
        } else {
            return Optional.of(userMapper.selectOneByExample(example).toModule());
        }
    }

    @Override
    public Optional<User> getById(Integer id) {
        Example example = new Example(UserDbo.class);
        example.createCriteria().andEqualTo("id", id);
        UserDbo userDbo = userMapper.selectOneByExample(example);
        if (userDbo == null) {
            return Optional.empty();
        } else {
            return Optional.of(userMapper.selectOneByExample(example).toModule());
        }
    }

    @Override
    public User updateNickname(User user) {
        UserDbo dbo = UserDbo.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .build();

        userMapper.updateByPrimaryKeySelective(dbo);
        return user;
    }
}
