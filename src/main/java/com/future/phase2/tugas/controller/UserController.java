package com.future.phase2.tugas.controller;

import com.future.phase2.tugas.model.User;
import com.future.phase2.tugas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "users/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> listOfUser(){
        return userService.getUserList();
    }

    @Cacheable(key = "#username", value = "user")
    @GetMapping(value = "users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User findUser(@PathVariable("username") String username){
        return userService.getUser(username);
    }

    @PostMapping(value = "users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveUser(@RequestBody User request){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole("MEMBER");

        if(userService.saveUser(user) == null)
            return "Insert Success";

        return "Save Failed";
    }

    @CacheEvict(key = "#request.getUsername()", value = "user")
    @PutMapping(value = "users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String editUser(@RequestBody User request){
        try{
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            User savedUser = userService.getUser(request.getUsername());
            if(savedUser == null)
                return "Username not found";

            savedUser.setName(request.getName());
            savedUser.setPassword(encoder.encode(request.getPassword()));

            if(userService.saveUser(savedUser) == null)
                return "Edit success";

        } catch (RuntimeException e){

        }

        return "Edit failed";
    }

    @DeleteMapping(value = "users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return "User deleted";
    }
}
