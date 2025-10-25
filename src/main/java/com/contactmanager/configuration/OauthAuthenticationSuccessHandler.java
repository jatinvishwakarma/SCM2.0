    package com.contactmanager.configuration;

    import java.io.IOException;
    import java.util.List;
    import java.util.UUID;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
    import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
    import org.springframework.security.web.DefaultRedirectStrategy;
    import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
    import org.springframework.stereotype.Component;

    import com.contactmanager.entities.Providers;
    import com.contactmanager.entities.User;
    import com.contactmanager.helper.AppConstants;
    import com.contactmanager.repositories.UserRepositories;

    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;

    @Component
    public class OauthAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

        Logger logger=LoggerFactory.getLogger(OauthAuthenticationSuccessHandler.class);

        @Autowired
        private UserRepositories userRepositories;

        @Override
        public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
                ) throws IOException, ServletException {

            logger.info("OauthAuthenticationSuccessHandler");
            
            // Identify the provider
            var oauth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;
            String authorizedClientRegistratoinId=oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

            DefaultOAuth2User oAuthUser=(DefaultOAuth2User)authentication.getPrincipal();
            
            User newUser=new User();
            newUser.setUserId(UUID.randomUUID().toString());
            newUser.setEnabled(true);
            newUser.setEmailVerified(true);
            newUser.setRoleList(List.of(AppConstants.Role_User));
            newUser.setPassword("not applicable");
        
                    
            // oAuthUser.getAttributes().forEach((key,value)->{

            // });

            // Hardcode 
            if(authorizedClientRegistratoinId.equalsIgnoreCase("google")){
                // Google or Google attributes
                newUser.setEmail(oAuthUser.getAttribute("email").toString());
                newUser.setProfilePic(oAuthUser.getAttribute("picture").toString());
                newUser.setName(oAuthUser.getAttribute("name").toString());
                newUser.setProviderUserId(oAuthUser.getName());
                newUser.setProvider(Providers.GOOGLE);
                newUser.setAbout("This account is created by Google");
                
            }
            else if(authorizedClientRegistratoinId.equalsIgnoreCase("github")){
                // Github or Github attributes
                String email=oAuthUser.getAttribute("email")!=null ?oAuthUser.getAttribute("email").toString() :
                                oAuthUser.getAttribute("login").toString()+"@gmail.com";
                String picture=oAuthUser.getAttribute("avatar_url").toString();
                String name=oAuthUser.getAttribute("login");
                String providerUserId=oAuthUser.getName();

                newUser.setEmail(email);
                newUser.setProfilePic(picture);
                newUser.setName(name);
                newUser.setProviderUserId(providerUserId);
                newUser.setProvider(Providers.GITHUB);
                newUser.setAbout("This account is created by Github");
            }
            else{
                logger.info("Unknown Provider....");
            }

            // Map<String, Object> attributes = user.getAttributes(); // ✅ Added to store all attributes
            // logger.info("OAuth Attributes: {}", attributes); // ✅ Added logging to see all returned attributes

            // // Fetching the data safely using helper function
            // String email = getSafeAttribute(user, "email", "login"); // ✅ Changed to avoid NPE, fallback for GitHub login
            // String name = getSafeAttribute(user, "name", "login");   // ✅ Changed to avoid NPE
            // String profilePic = getSafeAttribute(user, "picture", "avatar_url"); // ✅ Changed to avoid NPE

            // // Create a user for storing the data into the database for login because he can login
            // // because he can login
            // User newUser=new User();
            // newUser.setEmail(email);
            // newUser.setName(name);
            // newUser.setProfilePic(profilePic);
            // newUser.setUserId(UUID.randomUUID().toString());
            // newUser.setPassword("password");
            // newUser.setProvider(Providers.GOOGLE); // ✅ Optional: could detect dynamically if using multiple providers
            // // we need to make user enable becaue without enable he can't login becuase of the spring security and with the 
            // // oauth2 he can login but when he try to login manually he can't 
            // newUser.setEnabled(true);
            // newUser.setEmailVerified(true);
            // newUser.setProviderUserId(user.getName());
            // newUser.setRoleList(List.of(AppConstants.Role_User));
            // newUser.setAbout("This account is created using OAuth2-Google..");

            // Saving the data
            User userExist=userRepositories.findByEmail(newUser.getEmail()).orElse(null);
            if(userExist==null) userRepositories.save(newUser);
            
            new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
        }

        // // ✅ Added helper method to safely fetch attributes and avoid NullPointerException
        // private String getSafeAttribute(DefaultOAuth2User user, String... keys) {
        //     for (String key : keys) {
        //         Object value = user.getAttribute(key);
        //         if (value != null) return value.toString();
        //     }
        //     return null;
        // }}

    }