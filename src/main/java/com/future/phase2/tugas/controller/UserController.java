package com.future.phase2.tugas.controller;

import com.future.phase2.tugas.model.User;
import com.future.phase2.tugas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@EnableSwagger2
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

        User user = new User();
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setPassword(request.getPassword());
        user.setRole("MEMBER");

        if(userService.saveUser(user) != null)
            return "Insert Success";

        return "Save Failed";
    }

    @CacheEvict(key = "#request.getUsername()", value = "user")
    @PutMapping(value = "users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String editUser(@RequestBody User request){
        try{
            User savedUser = userService.getUser(request.getUsername());
            if(!savedUser.getUsername().equals(request.getUsername()))
                return "Username not found";

            savedUser.setName(request.getName());
            savedUser.setPassword(request.getPassword());

            if(userService.saveUser(savedUser) != null)
                return "Edit success";

        } catch (RuntimeException e){

        }

        return null;
    }
}
