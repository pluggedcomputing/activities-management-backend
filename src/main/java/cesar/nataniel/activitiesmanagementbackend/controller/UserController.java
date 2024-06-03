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
    private final ResponseRepository responseRepository;

    @Autowired
    public UserController(UserRepository userRepository, ResponseRepository responseRepository) {
        this.userRepository = userRepository;
        this.responseRepository = responseRepository;
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


    // Endpoint to get responses of a unique user
    @GetMapping("/getUniqueUser")
    public List<Response> getResponsesOfUser(
            @RequestParam String userName,
            @RequestParam String idApp,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        // Filter questions by userName and idApp
        List<Response> responses = responseRepository.findAll().stream()
                .filter(q -> q.getUserName().equals(userName) && q.getIdApp().equals(idApp))
                .collect(Collectors.toList());

        // Checks if startDate and endDate have been provided
        if (startDate != null && endDate != null) {
            responses = responses.stream()
                    .filter(q -> (q.getDateResponse().equals(startDate) || q.getDateResponse().equals(endDate)) || (q.getDateResponse().after(startDate) &&
                            q.getDateResponse().before(endDate)))
                    .collect(Collectors.toList());
        }

        return responses;
    }


    // Endpoint to get statistics of a unique user
    @GetMapping("/getStatisticsUser")
    public UserStatistics getStatisticsUser(
            @RequestParam String userName,
            @RequestParam String idApp,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {


        UserStatistics userStatistics = new UserStatistics();
        List<Response> allResponses = getResponsesOfUser(userName, idApp, startDate, endDate);
        userStatistics = userStatistics.calculateStatistics(allResponses);


        System.out.println(userStatistics);
        return userStatistics;
    }


}
