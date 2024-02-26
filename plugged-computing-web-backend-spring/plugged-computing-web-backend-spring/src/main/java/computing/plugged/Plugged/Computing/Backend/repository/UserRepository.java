package computing.plugged.Plugged.Computing.Backend.repository;

import computing.plugged.Plugged.Computing.Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
