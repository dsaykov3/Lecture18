package com.progress.controller;

import com.progress.model.Contact;
import com.progress.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class ContactController {

    @Autowired
    ContactService contactService;

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    @RequestMapping(value = "/")
    public ModelAndView listContact(ModelAndView model, Principal principal) throws IOException {
        List<Contact> listContact = contactService.getAllContacts();
        model.addObject("listContact", listContact);
        model.addObject("principal", principal);
        model.setViewName("home");

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

    @RequestMapping(value = "/unauthorized", method = {RequestMethod.GET,  RequestMethod.POST})
    @ResponseBody
    public String unauthorized() {
        return "Not authorized, sorry.";
    }

}
