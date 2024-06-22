package cesar.nataniel.activitiesmanagementbackend.repository;

import cesar.nataniel.activitiesmanagementbackend.model.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {

    List<Response> findByUserIDAndIdApp(String userID, String idApp);

    @Query("SELECT r FROM Response r WHERE r.userID = :userID AND r.idApp = :idApp AND r.dateResponse BETWEEN :startDate AND :endDate")
    List<Response> findByUserIDAndIdAppAndDateRange(@Param("userID") String userID, @Param("idApp") String idApp, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT r FROM Response r WHERE r.idApp = :idApp AND r.dateResponse BETWEEN :startDate AND :endDate")
    List<Response> findAllByDateRange(@Param("idApp") String idApp, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT r FROM Response r WHERE r.idApp = :idApp AND r.phase = :phase AND r.activity = :activity AND r.dateResponse BETWEEN :startDate AND :endDate")
    List<Response> findByIdAppAndPhaseAndActivityAndDateRange(@Param("idApp") String idApp, @Param("phase") String phase, @Param("activity") String activity, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<Response> findByIdAppAndPhaseAndActivity(String idApp,String phase, String activity);

    List<Response> findAllByIdApp(String idApp);

    @Query("SELECT DISTINCT idApp FROM Response ORDER BY idApp")
    List<String> findDistinctIdApp();

    @Query("SELECT DISTINCT r.userID FROM Response r WHERE r.idApp = :idApp ORDER BY r.userID")
    List<String> findDistinctUserIDByIdApp(@Param("idApp") String idApp);

    @Query("SELECT DISTINCT r.activity FROM Response r WHERE r.idApp = :idApp ORDER BY r.activity")
    List<String> findDistinctActivityByIdApp(@Param("idApp") String idApp);

    @Query("SELECT DISTINCT r.phase FROM Response r WHERE r.idApp = :idApp ORDER BY r.phase")
    List<String> findDistinctPhasesByIdApp(@Param("idApp") String idApp);
}
