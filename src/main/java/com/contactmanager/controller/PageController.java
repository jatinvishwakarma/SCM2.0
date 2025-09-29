package com.contactmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class PageController {
    @RequestMapping("/home")
    public String requestMethodName() {
        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage() {
        return "about";
    }
    @RequestMapping("/services")
    public String servicePage() {
        return "services";
    }
    
    

}
