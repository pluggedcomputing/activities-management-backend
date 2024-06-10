package cesar.nataniel.activitiesmanagementbackend.service;

import cesar.nataniel.activitiesmanagementbackend.model.Response;
import cesar.nataniel.activitiesmanagementbackend.model.ResponsesStatistics;
import cesar.nataniel.activitiesmanagementbackend.model.UserStatistics;
import cesar.nataniel.activitiesmanagementbackend.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponseService {
    private final ResponseRepository responseRepository;

    @Autowired
    public ResponseService(ResponseRepository responseRepository){
        this.responseRepository = responseRepository;
    }

    public UserStatistics getStatisticsUser(String userID, String idApp,
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        UserStatistics userStatistics = new UserStatistics();
        List<Response> allResponses = getResponsesOfUser(userID, idApp, startDate, endDate);
        userStatistics = userStatistics.calculateStatistics(allResponses);

        // Logging should be done using a logger
        System.out.println(userStatistics);
        return userStatistics;
    }

    public List<Response> getResponsesOfUser(String userID, String idApp,
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        // Filter questions by userID and idApp
        List<Response> responses = responseRepository.findAll().stream()
                .filter(q -> q.getUserID().equals(userID) && q.getIdApp().equals(idApp))
                .collect(Collectors.toList());

        // Checks if startDate and endDate have been provided
        if (startDate != null && endDate != null) {
            responses = responses.stream()
                    .filter(q -> (q.getDateResponse().equals(startDate) || q.getDateResponse().equals(endDate))
                            || (q.getDateResponse().after(startDate) && q.getDateResponse().before(endDate)))
                    .collect(Collectors.toList());
        }

        return sortResponseByDate(responses);
    }

    public ResponsesStatistics getStatisticsAllResponse(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        // Get all responses
        List<Response> responses = responseRepository.findAll();

        // Checks if startDate and endDate have been provided
        if (startDate != null && endDate != null) {
            responses = responses.stream()
                    .filter(q -> (q.getDateResponse().equals(startDate) || q.getDateResponse().equals(endDate))
                            || (q.getDateResponse().after(startDate) && q.getDateResponse().before(endDate)))
                    .collect(Collectors.toList());
        }

        // Generate statistics of responses
        ResponsesStatistics responsesStatistics = new ResponsesStatistics();
        responsesStatistics = responsesStatistics.calculateStatistics(responses);

        return responsesStatistics;
    }

    public ResponsesStatistics getStatisticsResponse(String phase, String activity,
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        ResponsesStatistics responsesStatistics = new ResponsesStatistics();
        List<Response> responses = getSearchResponse(phase, activity, startDate, endDate);

        responsesStatistics = responsesStatistics.calculateStatistics(responses);

        // Logging should be done using a logger
        System.out.println(responsesStatistics);
        return responsesStatistics;
    }

    public List<Response> getSearchResponse(String phase, String activity,
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        // Logging should be done using a logger
        System.out.println("phase: " + phase + " activity: " + activity);

        // Filter responses by phase and activity
        List<Response> searchResponse = new ArrayList<>();
        for (Response q: getAllResponse(startDate, endDate)) {
            if (q.getPhase().equalsIgnoreCase(phase) && q.getActivity().equalsIgnoreCase(activity)) {
                searchResponse.add(q);
            }
        }

        // Checks if startDate and endDate have been provided
        if (startDate != null && endDate != null) {
            searchResponse = searchResponse.stream()
                    .filter(q -> (q.getDateResponse().equals(startDate) || q.getDateResponse().equals(endDate))
                            || (q.getDateResponse().after(startDate) && q.getDateResponse().before(endDate)))
                    .collect(Collectors.toList());
        }

        // Logging should be done using a logger
        System.out.println(searchResponse);
        return sortResponseByDate(searchResponse);
    }

    public void createResponse(Response response) {
        Date now = new Date();
        response.setDateResponse(now);

        // Logging should be done using a logger
        System.out.println(response);
        responseRepository.save(response);
    }

    public List<Response> sortResponseByDate(List<Response> responses) {
        return responses.stream()
                .sorted(Comparator.comparing(Response::getDateResponse))
                .collect(Collectors.toList());
    }

    public List<Response> getAllResponse(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        List<Response> allResponses = responseRepository.findAll();

        // Checks if startDate and endDate have been provided
        if (startDate != null && endDate != null) {
            allResponses = allResponses.stream()
                    .filter(q -> (q.getDateResponse().equals(startDate) || q.getDateResponse().equals(endDate))
                            || (q.getDateResponse().after(startDate) && q.getDateResponse().before(endDate)))
                    .collect(Collectors.toList());
        }

        return sortResponseByDate(allResponses);
    }
}
