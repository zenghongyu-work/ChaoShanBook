package com.example.controller.assembler;

import com.example.app.user.UserApp;
import com.example.controller.user.UserResponse;
import com.example.domain.user.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler {

    @Value("${picture-base-url}")
    private String pictureBaseUrl;

    @Autowired
    private UserApp userApp;

    public UserResponse.SimpleUser assemblerUser(Integer id) {
        return toSimpleUser(userApp.getById(id));
    }

    private UserResponse.SimpleUser toSimpleUser(User user) {
        UserResponse.SimpleUser simpleUser = UserResponse.SimpleUser.builder().build();
        BeanUtils.copyProperties(user, simpleUser);

        if (StringUtils.isNotBlank(simpleUser.getIcon())) {
            simpleUser.setIcon(pictureBaseUrl + simpleUser.getIcon());
        }

        return simpleUser;
    }
}
