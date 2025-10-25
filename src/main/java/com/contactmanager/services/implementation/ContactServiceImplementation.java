package com.contactmanager.services.implementation;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contactmanager.entities.Contact;
import com.contactmanager.helper.ResourseNotFoundException;
import com.contactmanager.repositories.ContactRepositories;
import com.contactmanager.services.ContactService;

@Service
public class ContactServiceImplementation implements ContactService {

    @Autowired
    private ContactRepositories contactRepositories;


    @Override
    public Contact save(Contact contact) {
        String contactId=UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepositories.save(contact);

    }

    @Override
    public Contact update(Contact contact) {
        return null;
    }

    @Override
    public List<Contact> getAll() {
        return contactRepositories.findAll();
    }

    @Override
    public Contact getById(String id) {
       return contactRepositories.findById(id).orElseThrow(()->
       new ResourseNotFoundException("Contact with this Id doesn't exist."));
    }

    @Override
    public void delete(String id) {
        Contact contact=contactRepositories.getById(id);
        contactRepositories.delete(contact);
    }

    @Override
    public List<Contact> search(String name, String email, String phoneNumber) {
        return null;
    }

    @Override
    public List<Contact> getByUserId(String userId) {
        return contactRepositories.findByUserId(userId);
    }

}
