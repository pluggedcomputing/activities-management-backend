package cesar.nataniel.activitiesmanagementbackend.repository;

import cesar.nataniel.activitiesmanagementbackend.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
