package com.progress.exceptions;

import com.progress.model.Contact;

public class CrudValidationException extends RuntimeException {
    Contact contact;

    public CrudValidationException(Contact contact, String message) {
        super(message);
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
