package com.example.controller.assembler;

import com.example.app.user.UserApp;
import com.example.controller.media.MediaResponse;
import com.example.controller.user.UserResponse;
import com.example.domain.user.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MediaAssembler {

    @Value("${picture-base-url}")
    private String pictureBaseUrl;

    @Autowired
    private UserApp userApp;

    public MediaResponse.MediaDetail assemblerUser(MediaResponse.MediaDetail media) {
        media.setCreator(toSimpleUser(userApp.getById(media.getMedia().getCreateBy())));
        return media;
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
