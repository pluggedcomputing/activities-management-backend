package cesar.nataniel.activitiesmanagementbackend.controller;

import cesar.nataniel.activitiesmanagementbackend.model.Response;
import cesar.nataniel.activitiesmanagementbackend.model.ResponsesStatistics;
import cesar.nataniel.activitiesmanagementbackend.model.UserStatistics;
import cesar.nataniel.activitiesmanagementbackend.service.ResponseService;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/response")
public class ResponseController {

    private final ResponseService responseService;
    @Autowired
    public ResponseController(ResponseService responseService) {
        this.responseService = responseService;
    }



    // Endpoint to create a new response
    @PostMapping
    public void createResponse(@RequestBody Response response) {
        responseService.createResponse(response);
    }


    // Endpoint get all responses
    @GetMapping
    public List<Response> getAllResponse(
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate)
    {
       return responseService.getAllResponse(startDate,endDate);
    }


    // Endpoint to search responses with phase and activity
    @GetMapping("/getSearchResponse")
    public List<Response> getSearchResponse(
            String phase,
            String activity,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate)
    {
        return responseService.getSearchResponse(phase,activity,startDate ,endDate);
    }


    // Endpoint get statistics for a unique question
    @GetMapping("/getStatisticsResponse")
    public ResponsesStatistics getStatisticsResponse(
            @RequestParam String phase,
            @RequestParam String activity,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate)
    {
        System.out.println("Phase: " + phase + " Activity: " + activity);
        return responseService.getStatisticsResponse(phase,activity,startDate,endDate);
    }


    // Endpoint get statistics for all response
    @GetMapping("/getStatisticsAllResponse")
    public ResponsesStatistics getStatisticsAllResponse(
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate)
    {
        return responseService.getStatisticsAllResponse(startDate,endDate);
    }


    // Endpoint to get responses of a unique user
    @GetMapping("/getUniqueUser")
    public List<Response> getResponsesOfUser(
            @RequestParam String userID,
            @RequestParam String idApp,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate)
    {
        return responseService.getResponsesOfUser(userID,idApp,startDate,endDate);
    }


    // Endpoint to get statistics of a unique user
    @GetMapping("/getStatisticsUser")
    public UserStatistics getStatisticsUser(
            @RequestParam String userID,
            @RequestParam String idApp,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate)
    {
        return responseService.getStatisticsUser(userID,idApp,startDate,endDate);
    }

}
