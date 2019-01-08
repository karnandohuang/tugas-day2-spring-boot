package com.future.phase2.tugas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

        @GetMapping(value = "/dashboard")
        public String dashboard() {
            return "dashboard";
        }
}