package com.testing.TestSys.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"","/"})
    public String home(){
        return "index";
    }

    @GetMapping({"/test"})
    public String contact(){
        return "test1";
    }
}
