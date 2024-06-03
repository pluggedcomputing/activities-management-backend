package cesar.nataniel.activitiesmanagementbackend.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @Column(nullable = false, name = "email")
    private String userID;

}
