package com.progress.service;

import com.progress.dao.ContactDAOJPARepository;
import com.progress.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@SessionScope
public class ShoppingCartService {

    @Autowired
    private ContactDAOJPARepository contactDAOJPARepository;

    private Map<Contact, Integer> cart = new HashMap<>();

    public Map<Contact, Integer> getCart() {
        return cart;
    }

    public void setCart(Map<Contact, Integer> cart) {
        this.cart = cart;
    }

    public void checkout(){

    }
}
