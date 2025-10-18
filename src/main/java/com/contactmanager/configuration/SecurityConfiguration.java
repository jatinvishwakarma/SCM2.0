package com.contactmanager.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import com.contactmanager.services.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfiguration {
    // user creart and login useing java code with in memory
    // @Bean
    // public UserDetailsService userDetailService(){
    //     UserDetails userDetails=User
    //     .withUsername("jatin").password("{noop}jatin")
    //     .roles("ADMIN","USER")
    //     .build();
    //     var inMemoryUserDetailsManager= new InMemoryUserDetailsManager(userDetails);
    //     return inMemoryUserDetailsManager;
    // }

    @Autowired
    private SecurityCustomUserDetailService securityCustomUserDetailService;

    //  configuration for authentiation provider for spring security
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){

        // when we used spring security it needed the user data and password tbhi bhai uss passwrod ko encode krna padta tha
        // for production jruri hai bhai toh isiliye neeceh password encoder ka use kiye hai
        // baaki userdetailsservice jo hai woh database s intereact krega taaki hame data mile user ka ki woh 
        // secured page pe ja sakta hai ki nahi 

        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        // User service object
        daoAuthenticationProvider.setUserDetailsService(securityCustomUserDetailService);
        // password encoder object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    
    @Autowired
    private OauthAuthenticationSuccessHandler oauthAuthenticationSuccessHandler;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        // url configure kra hai konse public rhenge or konse private
        httpSecurity.authorizeHttpRequests(authorize ->{

            // isse hame access milta hai kis page ko ham visit kar sakte hai
            // authorize.requestMatchers("/home","/register","/login","/services").permitAll();

            // isse kya hoga ki ham secure krenge ki kis page ko secure hona hai 
            // difference ye hai ki upar wale me css files load nahi ho rhi 
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        // form default login
        // agar hame kuch nbhi change krna hua toh yaha aayenge form login s related
        httpSecurity.formLogin(formLogin->{
            // login page url
            formLogin.loginPage("/login")
            // processing of login page     
            .loginProcessingUrl("/authenticate")
            .defaultSuccessUrl("/user/dashboard", true)
            .failureUrl("/login?error=true")
            .usernameParameter("email")
            .passwordParameter("password")
            // .failureHandler(new AuthenticationFailureHandler() {

            //     @Override
            //     public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            //             AuthenticationException exception) throws IOException, ServletException {
            //         // TODO Auto-generated method stub
            //         throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationFailure'");
            //     }

            // })
            // .successHandler(new AuthenticationSuccessHandler() {

            //     @Override
            //     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            //             Authentication authentication) throws IOException, ServletException {
            //         // TODO Auto-generated method stub
            //         throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'");
            //     }
                
            // })
            ;
        });
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(logoutForm->{
            logoutForm.logoutUrl("/do-logout")
            .logoutSuccessUrl("/login?logout=true");
        });

        // ye isiliye banaya hai taaki agr koi user google se ya login wiith google yse kre toh redirect hojaye dashboard pe
        // login.html s woh database wale data se redirect krega
        // Oauth2 config
        httpSecurity.oauth2Login(oauth ->{
            oauth.loginPage("/login"); 
            oauth.successHandler(oauthAuthenticationSuccessHandler);
        });
        // we need to save the data into te database

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
