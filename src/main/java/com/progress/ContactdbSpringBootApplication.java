package com.progress;

import com.progress.model.GetContactRequest;
import com.progress.model.GetContactResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.xml.transform.StringSource;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;

@SpringBootApplication
public class ContactdbSpringBootApplication implements CommandLineRunner {
	@Autowired
	private WebServiceTemplate webServiceTemplate;
	public static void main(String[] args) {
		SpringApplication.run(ContactdbSpringBootApplication.class, args);
	}

	public void run(String... args) {
		final GetContactRequest contactRequest = new GetContactRequest();
		contactRequest.setId("27");
		GetContactResponse response= (GetContactResponse)webServiceTemplate.marshalSendAndReceive(contactRequest);
		System.out.println(response.getContact().toString());
	}

}
