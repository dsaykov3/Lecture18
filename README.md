# Lecture18
SOAP web service accessing Contact records is added to this project.
To call the service use the following curl or SOAP UI and WSDL from http://localhost:8080/ws/contacts.wsdl

```curl
curl --location --request POST 'localhost:8080/ws/' \
--header 'SOAPAction;' \
--data-raw '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:gs="http://spring.io/guides/gs-producing-web-service">
   <soapenv:Header/>
   <soapenv:Body>
      <gs:getContactRequest>
         <gs:id>31</gs:id>
      </gs:getContactRequest>
   </soapenv:Body>
</soapenv:Envelope>
```


RestController defined.
Spring Data Rest as well.
