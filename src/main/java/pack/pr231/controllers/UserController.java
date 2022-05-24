package pack.pr231.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pack.pr231.model.User;
import pack.pr231.repository.UserRepository;
import pack.pr231.service.UserService;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public String showAllPeople(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "all-users";
    }

    @GetMapping("/user/{id}")
    public String showPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findUser(id));
        return "user";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/all-users";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") long id, Model model) {
        User user = userService.findUser(id);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/updated/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        userService.updateUser(id, user);
        return "redirect:/all-users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        userService.delete(id);
        return "redirect:/all-users";
    }
}
