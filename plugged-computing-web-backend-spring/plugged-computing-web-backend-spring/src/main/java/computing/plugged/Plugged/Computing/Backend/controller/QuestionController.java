package computing.plugged.Plugged.Computing.Backend.controller;

import computing.plugged.Plugged.Computing.Backend.model.Question;
import computing.plugged.Plugged.Computing.Backend.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public void createResponseQuestion(@RequestBody Question question) {
        Date now = new Date();
        question.setDateResponse(now);
        questionRepository.save(question);
    }

    @GetMapping
    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }

    @GetMapping("/getUniqueUser")
    public List<Question> getQuestionOfUser(@RequestParam String idUser, @RequestParam String idApp) {
        // Filtrar as perguntas pelo idUser e pelo idApp
        return questionRepository.findAll().stream()
                .filter(q -> q.getIdUser().equals(idUser) && q.getIdApp().equals(idApp))
                .collect(Collectors.toList());
    }


    
}