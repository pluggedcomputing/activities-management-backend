package cesar.nataniel.activitiesmanagementbackend.controller;


import cesar.nataniel.activitiesmanagementbackend.model.Question;
import cesar.nataniel.activitiesmanagementbackend.model.User;
import cesar.nataniel.activitiesmanagementbackend.model.UserStatistics;
import cesar.nataniel.activitiesmanagementbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
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
