package cesar.nataniel.activitiesmanagementbackend.controller;

import cesar.nataniel.activitiesmanagementbackend.model.Response;
import cesar.nataniel.activitiesmanagementbackend.model.ResponsesStatistics;
import cesar.nataniel.activitiesmanagementbackend.repository.ResponseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/response")
public class ResponseController {

    private final ResponseRepository responseRepository;
    @Autowired
    public ResponseController(ResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }





    // Endpoint to create a new response
    @PostMapping
    public void createResponse(@RequestBody Response response) {
        Date now = new Date();
        response.setDateResponse(now);
        responseRepository.save(response);
    }


    // Endpoint to get all responses
    @GetMapping
    public List<Response> getAllResponse() {
        return responseRepository.findAll();
    }


    // Endpoint to search responses with phase and activity
    @GetMapping("/getSearchResponse")
    public List<Response> getSearchResponse(
            String phase,
            String activity,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {


        List<Response> searchResponse = new ArrayList<>();
        for (Response q: getAllResponse()){
            if (q.getPhase().equalsIgnoreCase(phase) && q.getActivity().equalsIgnoreCase(activity)){
                searchResponse.add(q);
            }
        }
        if (startDate != null && endDate != null) {
            searchResponse = searchResponse.stream()
                    .filter(q -> (q.getDateResponse().equals(startDate) || q.getDateResponse().after(startDate)) &&
                            (q.getDateResponse().equals(endDate) || q.getDateResponse().before(endDate)))
                    .collect(Collectors.toList());
        }

        return searchResponse;
    }


    // Endpoint to get statistics for a unique response
    @GetMapping("/getStatisticsResponse")
    public ResponsesStatistics getStatisticsResponse(
            @RequestParam String phase, @RequestParam String activity,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        ResponsesStatistics responsesStatistics = new ResponsesStatistics();
        for (Response q: getSearchResponse(phase,activity,startDate,endDate)) {
            responsesStatistics.addAnswer();
            if (q.getIsCorrect()) {
                responsesStatistics.addCorrectAnswers();
            } else {
                responsesStatistics.addWrongAnswers();
            }
        }
        responsesStatistics.calculatePercentageCorrectsAnswers();
        responsesStatistics.calculatePercentageWrongsAnswers();

        System.out.println(responsesStatistics);
        return responsesStatistics;
    }

}
