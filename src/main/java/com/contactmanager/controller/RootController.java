package com.contactmanager.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.contactmanager.entities.User;
import com.contactmanager.helper.Helper;
import com.contactmanager.services.UserService;

@ControllerAdvice
public class RootController {

    @Autowired  
    private UserService userService;

    private Logger logger=LoggerFactory.getLogger(this.getClass());
    @ModelAttribute
    public void addLoggedInUserInformation(Model model,Authentication authentication){
        if (authentication == null) {
        // no logged-in user, skip adding anything
        model.addAttribute("loggedInUser", null);
        return;
    }
        String userName=Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User logged in: {}",userName);
        // Database data ko fetch karna hai
        User user=userService.getUserByEmail(userName);
        System.out.println(user.getName());
        System.out.println(user.getEmail());
        model.addAttribute("loggedInUser", user);
    }
}
