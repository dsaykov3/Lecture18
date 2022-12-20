package com.progress.dao;

import com.progress.model.Contact;
//import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * Defines DAO operations for the contact model.
 * @author www.codejava.net
 *
 */
public interface ContactDAO {

	//@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	public void saveOrUpdate(Contact contact);
	//@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	public void delete(int contactId);
	
	public Contact get(int contactId);
	
	public List<Contact> list();
}
