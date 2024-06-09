package cesar.nataniel.activitiesmanagementbackend.model;
import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.Data;

@Data
@Entity
@Table(name = "tb-user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @Column(nullable = false)
    private String userID;

}
