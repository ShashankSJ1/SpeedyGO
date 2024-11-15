package com.happiest.APIGatewayJWT.APiGateway.Contact.controller;





import com.happiest.APIGatewayJWT.APiGateway.Contact.Repository.ContactServiceFeignClient;
import com.happiest.APIGatewayJWT.APiGateway.Contact.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private ContactServiceFeignClient contactServiceFeignClient;

    @PostMapping("/submit")
    public ResponseEntity<String> submitContact(@RequestBody Contact contact) {
        return contactServiceFeignClient.submitContact(contact);
    }
}

