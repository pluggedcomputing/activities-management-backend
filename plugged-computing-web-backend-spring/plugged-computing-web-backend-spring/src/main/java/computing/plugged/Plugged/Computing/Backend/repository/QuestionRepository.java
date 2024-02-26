package computing.plugged.Plugged.Computing.Backend.repository;

import computing.plugged.Plugged.Computing.Backend.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
