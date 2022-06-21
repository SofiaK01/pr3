package pack.pr231.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pack.pr231.model.User;
import pack.pr231.service.RightsRequestService;
import pack.pr231.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/request")
public class RequestController {

    private final UserService userService;

    private final RightsRequestService rightsRequestService;

    private final PasswordEncoder bCryptPasswordEncoder;

    public RequestController(UserService userService, PasswordEncoder bCryptPasswordEncoder, RightsRequestService rightsRequestService) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.rightsRequestService = rightsRequestService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<User>> list() {
        List<User> users = userService.listUsersAwaiting();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @PostMapping("/{id}")
    public ResponseEntity<String> reject(@PathVariable Integer id) {
        rightsRequestService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> accept(@RequestBody User user) {
        rightsRequestService.delete(user.getId());
        user.setPassword(userService.findUserById(user.getId()).getPassword());
        userService.updateUser(user.getId(), user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/awaiting")
    public ResponseEntity<Boolean> checkWaiting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByNickname(authentication.getName());
        System.out.println(rightsRequestService.checkById(user.getId()));
        return new ResponseEntity<>(rightsRequestService.checkById(user.getId()), HttpStatus.OK);
    }
}
