package com.contactmanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contactmanager.entities.Contact;
import com.contactmanager.entities.User;
@Repository
public interface ContactRepositories extends JpaRepository<Contact,String>{
    // Find the contacts of user
    List<Contact> findByUser(User user);

    @Query("Select c FROM Contact c WHERE c.user.id=:userId")
    List<Contact> findByUserId(@Param("userId") String userId);
}
