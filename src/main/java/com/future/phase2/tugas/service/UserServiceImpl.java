package com.future.phase2.tugas.service;

import com.future.phase2.tugas.model.User;
import com.future.phase2.tugas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String username) {
        return userRepository.findOne(username);
    }

    @Override
    public User saveUser(User user) {
        String validUser = "";
        if(user.getUsername() == null){
            validUser = "Username is empty";
        } else if(user.getName() == null){
            validUser = "Name is empty";
        } else if(user.getPassword() == null) {
            validUser = "Password is empty";
        }

        if(validUser.equals(""))
            userRepository.save(user);

        return user;
    }

    @Override
    public User deleteUser(String username) {
        User deleteUser = userRepository.findOne(username);
        userRepository.delete(deleteUser);
        return deleteUser;
    }


}
