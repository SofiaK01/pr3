package pack.pr231.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pack.pr231.model.User;
import pack.pr231.service.UserService;


@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/user-page")
    public String showUser() {
        return "userPage";
    }

    @GetMapping("/start")
    public String showStartPage(Model model) {
        model.addAttribute("user", new User());
        return "index";
    }

    @PostMapping("/signup")
    public String createUser(@ModelAttribute("user") User user, Model model) {
        userService.add(user);
        return "user";
    }


    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }

}

