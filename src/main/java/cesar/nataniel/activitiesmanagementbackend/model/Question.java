package cesar.nataniel.activitiesmanagementbackend.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.Date;

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
    private Boolean isCorrect;
    private Date dateResponse;
    private String typeOfQuestion;





}
