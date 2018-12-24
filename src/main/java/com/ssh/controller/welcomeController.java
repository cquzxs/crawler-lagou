package com.ssh.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class welcomeController {
    private static final Logger logger = Logger.getLogger(welcomeController.class);
    @RequestMapping("/getWelcomePage")
    public String getWelcomePage(){
        return "WelcomePage";
    }
}
