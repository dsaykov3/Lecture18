package com.progress.ws;

import com.progress.model.Contact;
import com.progress.model.GetContactRequest;
import com.progress.model.GetContactResponse;
import com.progress.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ContactEndpoint {
	private static final String NAMESPACE_URI = "http://progress.bg/web-service";

	@Autowired
	ContactService contactService;


	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getContactRequest")
	@ResponsePayload
	public GetContactResponse getContact(@RequestPayload GetContactRequest request) {
		GetContactResponse response= new GetContactResponse();
		Contact contact=contactService.getById(Integer.parseInt(request.getId()));
		response.setContact(contact);
		return response;
	}
}
