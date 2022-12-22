package com.example.demo.api;


import com.example.demo.model.Contact;
import com.example.demo.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/api")
public class RestApiController {
    public static Logger logger = LoggerFactory.getLogger(RestApiController.class);
    @Autowired
    ContactService contactService;

    @RequestMapping(value = "/contact/", method = RequestMethod.GET)
    public ResponseEntity<List<Contact>> listAllContact(){
        List<Contact> listContact = contactService.findAll();
        if(listContact.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Contact>>(listContact, HttpStatus.OK);
    }

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.GET)
    public Contact findContact(@PathVariable("id") long id){
        Contact contact = contactService.getOne(id);
        if(contact == null){
            ResponseEntity.notFound().build();
        }
        return contact;
    }

    @RequestMapping(value = "/contact/", method = RequestMethod.POST)
    public Contact saveContact(@Valid @RequestBody Contact contact){
        return contactService.save(contact);
    }

    public ResponseEntity<Contact> updateContact(@PathVariable(value = "id") long contactId,
                                                 @Valid @RequestBody Contact contactForm){
        Contact contact = contactService.getOne(contactId);
        if(contact == null){
            return ResponseEntity.notFound().build();
        }
        contact.setName(contactForm.getName());
        contact.setAge(contactForm.getAge());

        Contact updatedContact = contactService.save(contact);
        return ResponseEntity.ok(updatedContact);
    }

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Contact> deleteContact(@PathVariable(value = "id") long id){
        Contact contact = contactService.getOne(id);
        if(contact == null){
            return ResponseEntity.notFound().build();
        }

        contactService.delete(contact);
        return ResponseEntity.ok().build();
    }
}
