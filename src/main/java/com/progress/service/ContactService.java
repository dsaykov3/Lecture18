package com.progress.service;

import com.progress.dao.ContactDAOJPARepository;
import com.progress.dao.ContactGroupDAOJPARepository;
import com.progress.exceptions.CrudValidationException;
import com.progress.model.Contact;
import com.progress.model.ContactGroup;
import com.progress.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactGroupDAOJPARepository contactGroupDAO;

    @Autowired
    private ContactDAOJPARepository contactDAOJPARepository;


    public List<Contact> getAllContacts() {
        return contactDAOJPARepository.findAll();
    }

    public List<Contact> findBySearchTerm(String searhTerm) {
        return contactDAOJPARepository.findByNameLikeOrEmailLike(searhTerm + "%", searhTerm + "%");
    }

    public void deleteById(Integer contactId) {
        contactDAOJPARepository.deleteById(contactId);
    }

    public Contact getById(Integer contactId) {
        return contactDAOJPARepository.findById(contactId).orElse(null);
    }


    public List<ContactGroup> getAllContactGroups() {
        return contactGroupDAO.findAll();
    }


    @Transactional(Transactional.TxType.REQUIRED)
    //@PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public void saveOrUpdate(Contact contact) {

        if (!Validator.patternMatchesEmail(contact.getEmail())) {
            throw new CrudValidationException(contact, "The email is not valid");
        }
        contactDAOJPARepository.save(contact);
    }
}
