package com.contactmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.contactmanager.entities.User;
import com.contactmanager.forms.UserForm;
import com.contactmanager.helper.Message;
import com.contactmanager.helper.MessageType;
import com.contactmanager.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;





@Controller
public class PageController {
    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }
    
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
    @RequestMapping("/contact")
    public String contactPage() {
        return "contact";
    }
    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }
    @RequestMapping("/register")
    public String registerPage(Model model) {
        UserForm userForm=new UserForm();
        // userForm.setName("Jatin");
        // userForm.setAbout("He is just stared after wasting 10 months");
        model.addAttribute("userForm", userForm);
        return "register";
    }
    @RequestMapping(value="/do-register",method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm,BindingResult bindingResult,HttpSession session) {
        // Fetch the data
        
        // validate the data
        if(bindingResult.hasErrors())return "register";

        // save to database

        // userForm to user
        // userService

        // Using Builder
        // User user=User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .about(userForm.getAbout())
        // .password(userForm.getPassword())
        // .phoneNumber(userForm.getPhoneNumber())
        // .profilePic("https://www.istockphoto.com/vector/user-profile-icon-vector-avatar-or-person-icon-profile-picture-portrait-symbol-gm1451587807-488238421")
        // .build();

        User user=new User();
        user.setName(userForm.getName());
        user.setAbout(userForm.getAbout());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("https://www.istockphoto.com/vector/user-profile-icon-vector-avatar-or-person-icon-profile-picture-portrait-symbol-gm1451587807-488238421");

        
        userService.createUser(user);
        System.out.println("User Saved...");
        // message= successful
        Message message = Message.builder()
        .content("Registration Successful...")
        .type(MessageType.green)
        .build();
        
        
        session.setAttribute("message", message);
        return "redirect:register";

        // redirect
    }
    
    
    
    

}
