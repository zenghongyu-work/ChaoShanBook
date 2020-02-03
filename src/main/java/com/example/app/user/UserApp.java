package com.example.app.user;

import com.example.domain.user.User;
import com.example.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserApp {

    @Autowired
    private UserService userService;

    @Transactional(rollbackFor = Exception.class)
    public User register(User user) {
        return userService.add(user);
    }

    public User login(String name, String pd) {
        return userService.login(name, pd);
    }

    @Transactional(rollbackFor = Exception.class)
    public User update(User user) {
        return userService.update(user);
    }

    public User getById(Integer id) {
        return userService.getById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public User updateToken(User user) {
        return userService.updateToken(user);
    }
}
