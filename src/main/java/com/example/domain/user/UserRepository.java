package com.example.domain.user;

import java.util.Optional;

public interface UserRepository {

    User add(User user);

    User update(User user);

    User updateToken(User user);

    Optional<User> getByName(String name);

    Optional<User> getById(Integer id);

    User updateNickname(User user);
}
