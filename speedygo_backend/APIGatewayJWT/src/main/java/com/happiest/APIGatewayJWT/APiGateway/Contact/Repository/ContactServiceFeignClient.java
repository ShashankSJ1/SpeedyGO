package com.happiest.APIGatewayJWT.APiGateway.Contact.Repository;




import com.happiest.APIGatewayJWT.APiGateway.Contact.model.Contact;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

// The Feign client interface to call the contact service
@FeignClient(name = "http://ContactService/api/contact")
public interface ContactServiceFeignClient {

    @PostMapping("/submit")
    ResponseEntity<String> submitContact(@RequestBody Contact contact);
}
