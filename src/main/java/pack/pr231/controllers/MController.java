package pack.pr231.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class MController {

    @GetMapping(value = "/admin")
    public String showAllUsers() {
        return "allUsers";
    }

    @GetMapping(value = "/users")
    public String user() {
        return "userPage";
    }
}
