package com.contactmanager.services.implementation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.contactmanager.entities.User;
import com.contactmanager.helper.AppConstants;
import com.contactmanager.helper.ResourseNotFoundException;
import com.contactmanager.repositories.UserRepositories;
import com.contactmanager.services.UserService;

@Service
public class UserServiceImplementation implements UserService{

    @Autowired
    private UserRepositories userRepositories;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {

        // userId have to generate
        String userId=UUID.randomUUID().toString();
        user.setUserId(userId);

        // password encode
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set the user role
        user.setRoleList(List.of(AppConstants.Role_User));
        return userRepositories.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepositories.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
       User user2=userRepositories.findById(user.getUserId()).orElseThrow(() -> new ResourseNotFoundException());
    //    update user 2 from user 1
        user2.setName(user.getName());
        user2.setAbout(user.getAbout());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProfilePic(user.getProfilePic());
        user2.setProviderUserId(user.getProviderUserId());

        User savedUser=userRepositories.save(user2);
        return Optional.ofNullable(savedUser);
    }

    @Override
    public void deleteUser(String id) {
        User user2=userRepositories.findById(id).orElseThrow(()
         -> new ResourseNotFoundException());
        userRepositories.delete(user2);
    }

    @Override
    public boolean isUserExist(String userId) {
        User user2=userRepositories.findById(userId).orElse(null);
        return user2 !=null ? true:false; 
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user2=userRepositories.findByEmail(email).orElse(null);
        return user2!=null ? true:false;
    }

    @Override
    public List<User> getAllUser() {
        return userRepositories.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepositories.findByEmail(email).orElseThrow(()->
        new ResourseNotFoundException("User Not Found..."));
    }

}
