package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestLoginController {

    @RequestMapping("/admin/test")
    public String testAdmin() {

        return "login/test-admin.html";
    }

    @RequestMapping("/user/test")
    public String testUser() {

        return "login/test-user.html";
    }

}
