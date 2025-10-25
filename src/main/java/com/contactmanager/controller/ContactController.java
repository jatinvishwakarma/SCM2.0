package com.contactmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.contactmanager.entities.Contact;
import com.contactmanager.entities.User;
import com.contactmanager.forms.ContactForm;
import com.contactmanager.helper.Helper;
import com.contactmanager.helper.Message;
import com.contactmanager.helper.MessageType;
import com.contactmanager.services.ContactService;
import com.contactmanager.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    // Add contact page handler
    @RequestMapping("/add")
    public String addContactView(Model model){
        ContactForm contactForm=new ContactForm();
        model.addAttribute("contactForm", contactForm);
        return "user/add_contacts";
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String savingContact(
            @Valid @ModelAttribute ContactForm contactForm,BindingResult result,
            Authentication authentication,HttpSession httpSession) {    

                if(result.hasErrors()){
                    httpSession.setAttribute("message",Message.builder().content("Please correct the following errors.").type(MessageType.red).build());
                    return "user/add_contacts";
                }

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);

        // Handle file upload safely
        if (contactForm.getProfilePic() != null && !contactForm.getProfilePic().isEmpty()) {
            System.out.println("File uploaded: " + contactForm.getProfilePic().getOriginalFilename());
            // Example: You can upload to Cloudinary/AWS or store temporarily
        } else {
            System.out.println("No file uploaded, setting default image...");
        }

        // map form to entity
        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAdress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setFavourite(contactForm.isFavourite());
        contact.setLinkedinLink(contactForm.getLinkedinLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setUser(user);

        // save to DB
        contactService.save(contact);
        httpSession.setAttribute("message",Message.builder().content("Your Contact have been successfully savd.").type(MessageType.green).build());

        return "redirect:/user/contacts/add";
    }
}
