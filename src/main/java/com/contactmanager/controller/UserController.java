package com.contactmanager.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.contactmanager.entities.User;
import com.contactmanager.helper.Helper;
import com.contactmanager.services.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired  
    private UserService userService;

    // user dashhbord page
    @RequestMapping(value="/dashboard", method=RequestMethod.GET)
    public String userDashBoard() {
        return "user/dashboard";
    }

    // user profile page
    @RequestMapping(value="/profile", method=RequestMethod.GET)
    public String userprofile(Model model,  Authentication authentication) {
        
        return "user/profile";
    }
    
    // user add contact page

    // user view page

    // user edit page

    // user delete page

    // user contacts page

    // user search page

}
