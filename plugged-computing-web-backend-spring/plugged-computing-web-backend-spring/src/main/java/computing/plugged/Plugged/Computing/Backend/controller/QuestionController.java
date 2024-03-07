package computing.plugged.Plugged.Computing.Backend.controller;

import computing.plugged.Plugged.Computing.Backend.model.Question;
import computing.plugged.Plugged.Computing.Backend.model.User;
import computing.plugged.Plugged.Computing.Backend.repository.QuestionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        questionRepository.save(question);
        System.out.println("Response question created: " + question);

    }

    @GetMapping
    public List<Question> getAllQuestion(){
        return questionRepository.findAll();
    }


}
