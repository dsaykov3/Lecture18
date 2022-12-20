package com.progress.controller;

import com.progress.model.Contact;
import com.progress.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ContactRestController {
    @Autowired
    ContactService contactService;

    @RequestMapping(value = "/rest/find", method = RequestMethod.GET)
    public Contact find(@RequestParam("id") long id) {
        Contact contact = contactService.getById((int) id);
        return contact;
    }

    @RequestMapping(value = "/rest/find/{id}", method = RequestMethod.GET)
    public ResponseEntity<Contact> findbyPath(@PathVariable("id") long id) {
        Contact contact = contactService.getById((int) id);
        Map<String, List<String>> headers=new HashMap<>();
        headers.put("header1", Collections.singletonList("value1"));
        ResponseEntity<Contact> response = new ResponseEntity<>(contact, CollectionUtils.toMultiValueMap(headers), HttpStatus.OK);
        return response;
    }


}
