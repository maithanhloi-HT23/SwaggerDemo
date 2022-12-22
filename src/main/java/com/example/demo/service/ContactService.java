package com.example.demo.service;

import com.example.demo.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactService extends JpaRepository<Contact, Long> {

}
