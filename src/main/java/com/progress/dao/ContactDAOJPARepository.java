package com.progress.dao;

import com.progress.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactDAOJPARepository extends JpaRepository<Contact, Integer> {

    List<Contact> findByNameLikeOrEmailLike(String name,  String email);

}
