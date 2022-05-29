package pack.pr231.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import pack.pr231.model.Role;
import pack.pr231.model.User;
import pack.pr231.repository.RoleRepository;
import pack.pr231.service.UserService;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private UserService userService;
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/all-users")
    public String userList(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "admin/all-users";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") int id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "/admin/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") int id, WebRequest request) {
        Set<Role> roles = new HashSet<>();

        Role ur_user;
        if (Objects.equals(request.getParameter("role_user"), "on")) {
            ur_user = roleRepository.findRoleByName("ROLE_USER");
            roles.add(ur_user);
        }
        if (Objects.equals(request.getParameter("role_admin"), "on")) {
            ur_user = roleRepository.findRoleByName("ROLE_ADMIN");
            roles.add(ur_user);
        }
        user.setRoles(roles);
        userService.updateUser(id, user);
        return "redirect:/admin/all-users";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/admin/all-users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id, Model model) {
        userService.delete(id);
        return "redirect:/admin/all-users";
    }

}