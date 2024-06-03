package cesar.nataniel.activitiesmanagementbackend.controller;

import cesar.nataniel.activitiesmanagementbackend.model.Response;
import cesar.nataniel.activitiesmanagementbackend.model.User;
import cesar.nataniel.activitiesmanagementbackend.model.UserStatistics;
import cesar.nataniel.activitiesmanagementbackend.repository.ResponseRepository;
import cesar.nataniel.activitiesmanagementbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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



    // Endpoint to get all users
    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll();
    }


    // Endpoint to create a user
    @PostMapping
    public void createUser(@RequestBody User user) {
        userRepository.save(user);
        String userName = user.getUserName();
        System.out.println("User created: " + userName);

    }
}
