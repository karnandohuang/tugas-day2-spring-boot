package com.future.phase2.tugas.controller;

import com.future.phase2.tugas.model.Users;
import com.future.phase2.tugas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
//import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "users/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Users> listOfUser(){
        return userService.getUserList();
    }

    @Cacheable(key = "#username", value = "user")
    @GetMapping(value = "users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Users findUser(@PathVariable("username") String username){
        return userService.getUser(username);
    }

    @PostMapping(value = "admin/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveUser(@RequestBody Users request){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Users users = new Users();
        users.setUsername(request.getUsername());
        users.setName(request.getName());
        users.setPassword(encoder.encode(request.getPassword()));
        users.setRole("ROLE_MEMBER");

        if(userService.saveUser(users) == null)
            return "Insert Success";

        return "Save Failed";
    }

    @CacheEvict(key = "#request.getUsername()", value = "user")
    @PutMapping(value = "admin/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String editUser(@RequestBody Users request){
        try{
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            Users savedUsers = userService.getUser(request.getUsername());
            if(savedUsers == null)
                return "Username not found";

            savedUsers.setName(request.getName());
            savedUsers.setPassword(encoder.encode(request.getPassword()));

            if(userService.saveUser(savedUsers) == null)
                return "Edit success";

        } catch (RuntimeException ignored){

        }

        return "Edit failed";
    }

    @DeleteMapping(value = "admin/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return "Users deleted";
    }
}
