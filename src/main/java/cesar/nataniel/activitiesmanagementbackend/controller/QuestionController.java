package cesar.nataniel.activitiesmanagementbackend.controller;

import cesar.nataniel.activitiesmanagementbackend.model.Question;
import cesar.nataniel.activitiesmanagementbackend.model.ResponsesStatistics;
import cesar.nataniel.activitiesmanagementbackend.model.UserStatistics;
import cesar.nataniel.activitiesmanagementbackend.repository.QuestionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/question")
public class QuestionController {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    // Endpoint para criar uma nova pergunta
    @PostMapping
    public void createResponseQuestion(@RequestBody Question question) {
        Date now = new Date();
        question.setDateResponse(now);
        questionRepository.save(question);
    }

    // Endpoint para obter todas as perguntas
    @GetMapping
    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }

    // Endpoint para obter as perguntas de um usuário específico
    @GetMapping("/getUniqueUser")
    public List<Question> getQuestionOfUser(
            @RequestParam String idUser,
            @RequestParam String idApp,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        // Filtrar as perguntas pelo usuário e pela aplicação
        List<Question> questions = questionRepository.findAll().stream()
                .filter(q -> q.getIdUser().equals(idUser) && q.getIdApp().equals(idApp))
                .collect(Collectors.toList());

        // Verificar se as datas de início e término foram fornecidas
        if (startDate != null && endDate != null) {
            questions = questions.stream()
                    .filter(q -> q.getDateResponse().after(startDate) && q.getDateResponse().before(endDate))
                    .collect(Collectors.toList());
        }

        return questions;
    }

    // Endpoint para obter as estatísticas de um usuário específico
    @GetMapping("/getStatisticsUser")
    public UserStatistics getStatisticsUser(
            @RequestParam String idUser, @RequestParam String idApp,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        UserStatistics userStatistics = new UserStatistics();
        for (Question q: getQuestionOfUser(idUser, idApp, startDate, endDate)) {
            userStatistics.addAnswer();
            if (q.getIsCorrect()) {
                userStatistics.addCorrectAnswers();
            } else {
                userStatistics.addWrongAnswers();
            }
        }
        userStatistics.calculatePercentageCorrectsAnswers();
        userStatistics.calculatePercentageWrongsAnswers();

        System.out.println(userStatistics);
        return userStatistics;
    }

    // Endpoint para pesquisar perguntas com base na fase e na atividade
    @GetMapping("/getSearchQuestion")
    public List<Question> getSearchQuestion(
            String fase,
            String atividade,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {


        List<Question> searchQuestion = new ArrayList<>();
        for (Question q: getAllQuestion()){
            if (q.getPhaseActivity().toLowerCase().equals(fase.toLowerCase()) && q.getNumberActivity().toLowerCase().equals(atividade.toLowerCase())){
                searchQuestion.add(q);
            }
        }
        if (startDate != null && endDate != null) {
            searchQuestion = searchQuestion.stream()
                    .filter(q -> q.getDateResponse().after(startDate) && q.getDateResponse().before(endDate))
                    .collect(Collectors.toList());
        }
        return searchQuestion;
    }

    // Endpoint para obter as estatísticas de uma questão específica
    @GetMapping("/getStatisticsResponse")
    public ResponsesStatistics getStatisticsResponse(
            @RequestParam String fase, @RequestParam String atividade,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        ResponsesStatistics responsesStatistics = new ResponsesStatistics();
        for (Question q: getSearchQuestion(fase,atividade,startDate,endDate)) {
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
