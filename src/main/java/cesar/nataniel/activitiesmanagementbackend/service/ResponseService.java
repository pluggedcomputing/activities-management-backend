package cesar.nataniel.activitiesmanagementbackend.service;

import cesar.nataniel.activitiesmanagementbackend.model.Response;
import cesar.nataniel.activitiesmanagementbackend.model.ResponsesStatistics;
import cesar.nataniel.activitiesmanagementbackend.model.UserStatistics;
import cesar.nataniel.activitiesmanagementbackend.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponseService {
    private final ResponseRepository responseRepository;

    @Autowired
    public ResponseService(ResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    /**
     * Returns and calculates user statistics for the given user ID and app ID within the specified date range.
     *
     * @param userID The ID of the user.
     * @param idApp The ID of the application.
     * @param startDate The start date of the date range.
     * @param endDate The end date of the date range.
     * @return Calculated user statistics.
     */
    public UserStatistics getStatisticsUser(
            String userID, String idApp,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        List<Response> allResponses = getResponsesOfUser(userID, idApp, startDate, endDate);
        return new UserStatistics().calculateStatistics(allResponses);
    }

    /**
     * Returns responses of a user within the specified date range.
     *
     * @param userID The ID of the user.
     * @param idApp The ID of the application.
     * @param startDate The start date of the date range.
     * @param endDate The end date of the date range.
     * @return List of responses sorted by date.
     */
    public List<Response> getResponsesOfUser(
            String userID, String idApp,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        List<Response> responses;
        if (startDate != null && endDate != null) {
            responses = responseRepository.findByUserIDAndIdAppAndDateRange(userID, idApp, startDate, endDate);
        } else {
            responses = responseRepository.findByUserIDAndIdApp(userID, idApp);
        }
        return sortResponseByDate(responses);
    }

    /**
     * Returns and calculates statistics for all responses within the specified date range.
     *
     * @param idApp The ID of the application.
     * @param startDate The start date of the date range.
     * @param endDate The end date of the date range.
     * @return Calculated statistics for all responses.
     */
    public ResponsesStatistics getStatisticsAllResponse(
            String idApp,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        List<Response> responses;
        if (startDate != null && endDate != null) {
            responses = responseRepository.findAllByDateRange(idApp, startDate, endDate);
        } else {
            responses = responseRepository.findAllByIdApp(idApp);
        }
        return new ResponsesStatistics().calculateStatistics(responses);
    }

    /**
     * Returns and calculates statistics for responses based on phase and activity within the specified date range.
     *
     * @param idApp The ID of the application.
     * @param phase The phase of the activity.
     * @param activity The activity.
     * @param startDate The start date of the date range.
     * @param endDate The end date of the date range.
     * @return Calculated statistics for the specified phase and activity.
     */
    public ResponsesStatistics getStatisticsResponse(
            String idApp, String phase, String activity,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        List<Response> responses = getSearchResponse(idApp, phase, activity, startDate, endDate);
        return new ResponsesStatistics().calculateStatistics(responses);
    }

    /**
     * Returns responses based on phase and activity within the specified date range.
     *
     * @param idApp The ID of the application.
     * @param phase The phase of the activity.
     * @param activity The activity.
     * @param startDate The start date of the date range.
     * @param endDate The end date of the date range.
     * @return List of responses sorted by date.
     */
    public List<Response> getSearchResponse(
            String idApp, String phase, String activity,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        List<Response> searchResponse;
        if (startDate != null && endDate != null) {
            searchResponse = responseRepository.findByIdAppAndPhaseAndActivityAndDateRange(idApp, phase, activity, startDate, endDate);
        } else {
            searchResponse = responseRepository.findByIdAppAndPhaseAndActivity(idApp, phase, activity);
        }
        return sortResponseByDate(searchResponse);
    }

    /**
     * Creates a new response with the current date and saves it to the repository.
     *
     * @param response The response to be created.
     */
    public void createResponse(Response response) {
        response.setDateResponse(new Date());
        responseRepository.save(response);
    }

    /**
     * Sorts a list of responses by date.
     *
     * @param responses The list of responses to be sorted.
     * @return The sorted list of responses.
     */
    public List<Response> sortResponseByDate(List<Response> responses) {
        return responses.stream()
                .sorted(Comparator.comparing(Response::getDateResponse))
                .collect(Collectors.toList());
    }

    /**
     * Returns all responses within the specified date range.
     *
     * @param idApp The ID of the application.
     * @param startDate The start date of the date range.
     * @param endDate The end date of the date range.
     * @return List of all responses sorted by date.
     */
    public List<Response> getAllResponse(
            String idApp,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        List<Response> allResponses;

        if (startDate != null && endDate != null) {
            allResponses = responseRepository.findAllByDateRange(idApp,startDate, endDate);
        } else {
            allResponses = responseRepository.findAllByIdApp(idApp);
        }
        return sortResponseByDate(allResponses);
    }


    public List<String> getApplications() {
        return responseRepository.findDistinctIdApp();
    }

    public List<String> getUsers(String idApp) {
        return responseRepository.findDistinctUserIDByIdApp(idApp);
    }

    public List<String> getActivity(String idApp) {
        return responseRepository.findDistinctActivityByIdApp(idApp);
    }

    public List<String> getPhases(String idApp) {
        return responseRepository.findDistinctPhasesByIdApp(idApp);
    }
}
