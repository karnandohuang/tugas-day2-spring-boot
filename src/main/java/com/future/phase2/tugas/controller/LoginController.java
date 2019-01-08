package com.future.phase2.tugas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @GetMapping(value = "/login")
    public String doLogin() {
        return "login";
    }

    @GetMapping(value = "/")
    public String indexPage() {
        return "index";
    }
}