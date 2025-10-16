package com.contactmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/user")
public class UserController {

    // user dashhbord page
    @RequestMapping(value="/dashboard", method=RequestMethod.GET)
    public String userDashBoard() {
        return "user/dashboard";
    }

    // user profile page
    @RequestMapping(value="/profile", method=RequestMethod.GET)
    public String userprofile() {
        return "user/profile";
    }
    
    // user add contact page

    // user view page

    // user edit page

    // user delete page

    // user contacts page

    // user search page

}
