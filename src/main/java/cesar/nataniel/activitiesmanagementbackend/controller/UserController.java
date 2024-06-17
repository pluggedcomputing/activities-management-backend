package cesar.nataniel.activitiesmanagementbackend.controller;


import cesar.nataniel.activitiesmanagementbackend.model.User;
    import cesar.nataniel.activitiesmanagementbackend.repository.UserRepository;
    import cesar.nataniel.activitiesmanagementbackend.service.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.validation.annotation.Validated;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    // Endpoint to get all users
    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }


    // Endpoint to create a user
    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }


    @GetMapping("/getAllUserId")
    public List<String> getAllUserId(){
       return userService.getAllUserId();
    }
}
