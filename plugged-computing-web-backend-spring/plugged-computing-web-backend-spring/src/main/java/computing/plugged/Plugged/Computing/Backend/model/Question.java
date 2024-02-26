package computing.plugged.Plugged.Computing.Backend.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Question {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String idUser;
    private String idApp;
    private String phaseActivity;
    private String numberActivity;
    private String userResponse;
    private String expectedResponse;
    private boolean isCorrects;
    private String dateResponse;
    private String typeOfQuestion;





}
