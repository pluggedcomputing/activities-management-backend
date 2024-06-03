package cesar.nataniel.activitiesmanagementbackend.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Response {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String userID;
    private String idApp;
    private String phase;
    private String activity;
    private String userResponse;
    private String expectedResponse;
    private Boolean isCorrect;
    private Date dateResponse;
    private String typeOfQuestion;





}
