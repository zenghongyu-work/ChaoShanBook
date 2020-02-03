package com.example.domain.user;

import com.example.domain.execption.BusinessException;
import com.example.domain.execption.NotFoundException;
import com.example.infrastructure.utils.JwtUtils;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User add(User user) {
        if (userRepository.getByName(user.getName()).isPresent()) {
            throw new BusinessException("用户名已存在");
        }

        user.setNickname(user.getName());
        user.setPd(Base64.encodeBase64String(user.getPd().getBytes()));
        userRepository.add(user);
        user.setToken(JwtUtils.createJWT(String.valueOf(user.getId())));
        userRepository.updateToken(user);
        return user;
    }

    public User login(String name, String pd) {
        User user = userRepository.getByName(name).orElseThrow(() -> new BusinessException("用户不存在或密码不正确"));

        if (!user.getPd().equals(Base64.encodeBase64String(pd.getBytes()))) {
            throw new BusinessException("用户不存在或密码不正确");
        }

        user.setToken(JwtUtils.createJWT(String.valueOf(user.getId())));
        userRepository.updateToken(user);

        return user;
    }

    public User getById(Integer id) {
        return userRepository.getById(id).orElseThrow(() -> new NotFoundException(String.format("用户不存在（%d）", id)));
    }

    public User update(User user) {
        return userRepository.update(user);
    }

    public User increaseFollowCount(User user) {
        user.setFollowCount(user.getFollowCount() + 1);
        return userRepository.update(user);
    }

    public User increaseFanCount(User user) {
        user.setFanCount(user.getFanCount() + 1);
        return userRepository.update(user);
    }

    public User decreaseFollowCount(User user) {
        user.setFollowCount(user.getFollowCount() - 1 < 0 ? 0 : user.getFollowCount() - 1);
        return userRepository.update(user);
    }

    public User decreaseFanCount(User user) {
        user.setFanCount(user.getFanCount() - 1 < 0 ? 0 : user.getFanCount() - 1);
        return userRepository.update(user);
    }

    public User updateToken(User user) {
        user.setToken(JwtUtils.createJWT(String.valueOf(user.getId())));
        return userRepository.updateToken(user);
    }
}
