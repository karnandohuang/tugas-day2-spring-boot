package com.future.phase2.tugas.service;

import com.future.phase2.tugas.model.Users;
import com.future.phase2.tugas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Users> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public Users getUser(String username) {
        return userRepository.findOne(username);
    }

    @Override
    public String saveUser(Users users) {
        String validUser = null;
        if(users.getUsername() == null){
            validUser = "Username is empty";
        } else if(users.getName() == null){
            validUser = "Name is empty";
        } else if(users.getPassword() == null) {
            validUser = "Password is empty";
        }

        if(validUser == null)
            userRepository.save(users);

        return validUser;
    }

    @Override
    public Users deleteUser(String username) {
        Users deleteUsers = userRepository.findOne(username);
        userRepository.delete(deleteUsers);
        return deleteUsers;
    }


}
