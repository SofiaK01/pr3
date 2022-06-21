package pack.pr231.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import pack.pr231.model.User;
import pack.pr231.service.EmailService;
import pack.pr231.service.RightsRequestService;
import pack.pr231.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/email")
public class EmailController {

    private static final Logger LOG = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    EmailService emailService;
    @Autowired
    private UserService userService;
    @Autowired
    private RightsRequestService rightsRequestService;

    @PutMapping(value = "/admin-request/{id}")
    public @ResponseBody
    ResponseEntity<String> sendSimpleEmail(@PathVariable Integer id) {
        String message = "User with id " + id + " wants to become an admin";

        rightsRequestService.add(id);

        try {
            List<User> users = userService.listUsers();

            for (User u : users) {
                if (u.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                    emailService.sendSimpleEmail(u.getEmail(), "Admin request", message);
                }
            }

        } catch (MailException mailException) {
            LOG.error("Error while sending out email..{}", mailException.getStackTrace());
            LOG.error("Error while sending out email..{}", mailException.fillInStackTrace());
            return new ResponseEntity<>("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Please check your inbox", HttpStatus.OK);
    }


}