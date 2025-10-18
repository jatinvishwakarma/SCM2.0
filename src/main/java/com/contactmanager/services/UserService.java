package com.contactmanager.services;

import java.util.List;
import java.util.Optional;

import com.contactmanager.entities.User;

public interface UserService {
    User createUser(User user);
    Optional<User> getUserById(String id);
    Optional<User> updateUser(User user);
    void deleteUser(String id);
    boolean isUserExist(String userId);
    boolean isUserExistByEmail(String email);
    List<User> getAllUser();
    User getUserByEmail(String email);


}
