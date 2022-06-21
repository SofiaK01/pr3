package pack.pr231.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pack.pr231.model.User;
import pack.pr231.service.UserService;

@Controller
@RequestMapping("")
public class MController {
    @Autowired
    private UserService userService;
    @GetMapping(value = "/admin")
    public String showAllUsers() {
        return "allUsers";
    }

    @GetMapping(value = "/users")
    public String user() {
        return "userPage";
    }


}
