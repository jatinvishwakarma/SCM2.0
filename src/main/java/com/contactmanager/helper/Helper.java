package com.contactmanager.helper;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {
 
    public static String getEmailOfLoggedInUser(Authentication authentication){

        // if we used emailid to login 
        if(authentication instanceof OAuth2AuthenticationToken){
            var oauth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;
            var clentId=oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oauth2User=(OAuth2User)authentication.getPrincipal();
            String username="";


            if(clentId.equalsIgnoreCase("google")){
                username=oauth2User.getAttribute("email").toString();

            }else if(clentId.equalsIgnoreCase("github")){
                username=oauth2User.getAttribute("email")!=null ? oauth2User.getAttribute("email").toString()
                :oauth2User.getAttribute("login").toString()+"@gmail.com";
            }
            return username;
        }
        // or if we used google or guthub so how could we get the email
        return authentication.getName();
    
    }
}
