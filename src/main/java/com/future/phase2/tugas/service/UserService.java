package com.future.phase2.tugas.service;

import com.future.phase2.tugas.model.User;

import java.util.List;

public interface UserService {
    List<User> getUserList();
    User getUser(String username);
    User saveUser(User user);
    User deleteUser(String username);
}
