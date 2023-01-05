package com.progress.controller;

import com.progress.model.Contact;
import com.progress.service.ContactService;
import com.progress.service.ShoppingCartService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.List;

@Controller
public class ContactController {

    @Autowired
    ShoppingCartService shoppingCart;

    @Autowired
    ContactService contactService;

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    @RequestMapping(value = "/")
    public ModelAndView listContact(ModelAndView model, Principal principal) throws IOException {
        List<Contact> listContact = contactService.getAllContacts();
        model.addObject("listContact", listContact);
        model.addObject("principal", principal);
        model.addObject("cart", shoppingCart.getCart());
        model.setViewName("home");

        return model;
    }

    @RequestMapping(value = "/cart")
    public ModelAndView showCart(ModelAndView model, Principal principal) throws IOException {
        model.addObject("cart", shoppingCart.getCart());
        model.setViewName("cart");

        return model;
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public ModelAndView findContacts(@RequestParam("searhTerm") String searhTerm, ModelAndView model, Principal principal) throws IOException {
        List<Contact> listContacts = contactService.findBySearchTerm(searhTerm);
        model.addObject("listContact", listContacts);
        model.addObject("searhTerm", searhTerm);
        model.addObject("principal", principal);
        model.setViewName("home");
        return model;
    }

    @RequestMapping(value = "/newContact", method = RequestMethod.GET)
    public ModelAndView newContact(ModelAndView model) {
        Contact newContact = new Contact();
        model.addObject("contact", newContact);
        model.addObject("contactGroups", contactService.getAllContactGroups());
        model.setViewName("editContact");
        return model;
    }

    @RequestMapping(value = "/saveContact", method = RequestMethod.POST)
    public ModelAndView saveContact(@ModelAttribute Contact contact) {
        ModelAndView model = new ModelAndView("redirect:/");
        contactService.saveOrUpdate(contact);
        return model;
    }

    @RequestMapping(value = "/deleteContact", method = RequestMethod.GET)
    public ModelAndView deleteContact(HttpServletRequest request) {
        int contactId = Integer.parseInt(request.getParameter("id"));
        contactService.deleteById(contactId);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/editContact", method = RequestMethod.GET)
    public ModelAndView editContact(HttpServletRequest request) {
        int contactId = Integer.parseInt(request.getParameter("id"));
        Contact contact = contactService.getById(contactId);
        ModelAndView model = new ModelAndView("editContact");
        model.addObject("contact", contact);
        model.addObject("contactGroups", contactService.getAllContactGroups());

        return model;
    }

    @RequestMapping(value = "/addContactToCart", method = RequestMethod.GET)
    public ModelAndView addContactToShoppingCart(HttpServletRequest request, Principal principal) {
        int contactId = Integer.parseInt(request.getParameter("id"));
        Contact contact = contactService.getById(contactId);
        List<Contact> listContacts = contactService.findBySearchTerm("");
        ModelAndView model = new ModelAndView("home");
        model.addObject("listContact", listContacts);
        model.addObject("principal", principal);

        shoppingCart.getCart().put(contact, shoppingCart.getCart().getOrDefault(contact, 0) + 1);
        model.addObject("cart", shoppingCart.getCart());

        return model;
    }


    @RequestMapping(value = "/image/{path}", method = RequestMethod.GET)
    public @ResponseBody byte[] getImage(@PathVariable String path) throws IOException {
        InputStream in = new BufferedInputStream(new FileInputStream("C:\\projects\\" + path+".jpg"));
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = in.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        return buffer.toByteArray();
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login");

        return model;
    }

    @RequestMapping(value = "/unauthorized", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String unauthorized() {
        return "Not authorized, sorry.";
    }

}
