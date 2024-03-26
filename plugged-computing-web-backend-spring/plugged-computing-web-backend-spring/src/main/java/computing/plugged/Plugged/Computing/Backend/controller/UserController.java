package computing.plugged.Plugged.Computing.Backend.controller;


import computing.plugged.Plugged.Computing.Backend.model.User;
import computing.plugged.Plugged.Computing.Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        userRepository.save(user);
        String email = user.getEmail();
        System.out.println("User created: " + email);

    }

}
