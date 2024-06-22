package cesar.nataniel.activitiesmanagementbackend.repository;

import cesar.nataniel.activitiesmanagementbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    @Query("SELECT DISTINCT userID FROM User ORDER BY userID")
    List<String> findDistinctUserID();

}
