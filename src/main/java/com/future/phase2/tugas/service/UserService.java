package com.future.phase2.tugas.service;

import com.future.phase2.tugas.model.Users;

import java.util.List;

public interface UserService {
    List<Users> getUserList();
    Users getUser(String username);
    String saveUser(Users users);
    Users deleteUser(String username);
}
