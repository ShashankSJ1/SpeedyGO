package com.happiest.ContactService.controller;

import com.happiest.ContactService.constants.PredefinedConstant;
import com.happiest.ContactService.model.Contact;
import com.happiest.ContactService.service.ContactService;
import com.happiest.ContactService.utility.Rbundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    private static final Logger LOGGER = LogManager.getLogger(ContactController.class);

    /**
     * Handles contact form submissions.
     *
     * @param contact The contact information submitted by the user.
     * @return A ResponseEntity containing the result of the contact submission:
     *         - 200 OK if successful
     *         - 500 Internal Server Error if an error occurs during processing.
     */
    @Operation(summary = "Submit a contact form", description = "Submits contact information to the service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact submitted successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/submit")
    public ResponseEntity<String> submitContact(@RequestBody Contact contact) {
        try {
            String receivedMessage = Rbundle.getKey(PredefinedConstant.CONTACT_RECEIVED);
            LOGGER.info(receivedMessage + ": {}"+ contact);
            contactService.saveContact(contact);
            String successMessage = Rbundle.getKey(PredefinedConstant.CONTACT_SUBMIT_SUCCESS);
            LOGGER.info(successMessage + contact.getEmail());
            return ResponseEntity.ok(successMessage);
        } catch (Exception e) {
            String errorMessage = Rbundle.getKey(PredefinedConstant.CONTACT_SUBMIT_ERROR) + ": " + e.getMessage();
            LOGGER.error(errorMessage, e);

            return ResponseEntity.status(500).body(errorMessage);
        }


    }
}

