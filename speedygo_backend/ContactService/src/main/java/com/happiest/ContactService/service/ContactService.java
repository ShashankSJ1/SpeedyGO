package com.happiest.ContactService.service;

import com.happiest.ContactService.constants.PredefinedConstant;
import com.happiest.ContactService.model.Contact;
import com.happiest.ContactService.repository.ContactRepository;
import com.happiest.ContactService.utility.Rbundle;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    private static final Logger LOGGER = LogManager.getLogger(ContactService.class);


    /**
     * Saves the contact details to the repository.
     *
     * @param contact The contact information to be saved.
     * @throws RuntimeException if any validation or saving error occurs.
     */
    public void saveContact(Contact contact) {
        try {
            LOGGER.info("Attempting to save contact: {}"+ contact);

            if (contact.getEmail() == null || contact.getName() == null || contact.getMessage() == null) {

                LOGGER.error(Rbundle.getKey(PredefinedConstant.CONTACT_INVALID_INPUT));
                throw new IllegalArgumentException(Rbundle.getKey(PredefinedConstant.CONTACT_INVALID_INPUT));
            }
            contactRepository.save(contact);
            String successMessage = Rbundle.getKey(PredefinedConstant.CONTACT_SAVE_SUCCESS);
            LOGGER.info(successMessage + ": {}"+ contact);

        } catch (IllegalArgumentException e) {
            String errorMessage = Rbundle.getKey(PredefinedConstant.CONTACT_SAVE_ERROR) + ": " + e.getMessage();
            LOGGER.error(errorMessage, e);
            throw new RuntimeException(errorMessage);
        } catch (Exception e) {
            String errorMessage = Rbundle.getKey(PredefinedConstant.CONTACT_SAVE_ERROR) + ": " + e.getMessage();
            LOGGER.error(errorMessage, e);
            throw new RuntimeException(errorMessage);
        }
    }
}


