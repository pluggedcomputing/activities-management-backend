package cesar.nataniel.activitiesmanagementbackend.repository;

import cesar.nataniel.activitiesmanagementbackend.model.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {

    List<Response> findByUserIDAndIdApp(String userID, String idApp);

    @Query("SELECT r FROM Response r WHERE r.userID = :userID AND r.idApp = :idApp AND r.dateResponse BETWEEN :startDate AND :endDate")
    List<Response> findByUserIDAndIdAppAndDateRange(@Param("userID") String userID, @Param("idApp") String idApp, @Param("startDate") LocalDate  startDate, @Param("endDate") LocalDate  endDate);

    @Query("SELECT r FROM Response r WHERE r.idApp = :idApp AND r.dateResponse BETWEEN :startDate AND :endDate")
    Page<Response> findAllByDateRange(@Param("idApp") String idApp, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);

    @Query("SELECT r FROM Response r WHERE r.idApp = :idApp AND r.dateResponse BETWEEN :startDate AND :endDate")
    List<Response> findAllByDateRange(@Param("idApp") String idApp, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    @Query("SELECT r FROM Response r WHERE r.idApp = :idApp AND r.phase = :phase AND r.activity = :activity AND r.dateResponse BETWEEN :startDate AND :endDate")
    List<Response> findByIdAppAndPhaseAndActivityAndDateRange(@Param("idApp") String idApp, @Param("phase") String phase, @Param("activity") String activity, @Param("startDate") LocalDate  startDate, @Param("endDate") LocalDate  endDate);

    List<Response> findByIdAppAndPhaseAndActivity(String idApp,String phase, String activity);

    Page<Response> findAllByIdApp(String idApp, Pageable pageable);
    List<Response> findAllByIdApp(String idApp);
    @Query("SELECT DISTINCT idApp FROM Response ORDER BY idApp")
    List<String> findDistinctIdApp();

    @Query("SELECT DISTINCT r.userID FROM Response r WHERE r.idApp = :idApp ORDER BY r.userID")
    List<String> findDistinctUserIDByIdApp(@Param("idApp") String idApp);

    @Query("SELECT DISTINCT r.activity FROM Response r WHERE r.idApp = :idApp AND r.phase = :phase ORDER BY r.activity")
    List<String> findDistinctActivityByIdAppAndPhase(@Param("idApp") String idApp, @Param("phase") String phase);

    @Query("SELECT DISTINCT r.phase FROM Response r WHERE r.idApp = :idApp ORDER BY r.phase")
    List<String> findDistinctPhasesByIdApp(@Param("idApp") String idApp);
}
