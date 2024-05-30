package cesar.nataniel.activitiesmanagementbackend.repository;

import cesar.nataniel.activitiesmanagementbackend.model.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {

}
