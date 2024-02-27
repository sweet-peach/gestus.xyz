package xyz.gestus.gestus.feature.logs;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xyz.gestus.gestus.feature.user.User;

import java.util.List;
import java.util.Map;

public interface LogRepository extends JpaRepository<Log,Long> {
    public List<Log> getLogModelByUserId(Long userId);

    @Query("SELECT l.user, COUNT(l) as logCount FROM Log l GROUP BY l.user ORDER BY logCount DESC")
    List<Object[]> findTopUsersWithMostLogs(Pageable pageable);

    @Query("SELECT new map(DATE(l.date) as date, COUNT(l) as count) FROM Log l WHERE l.user.id = :userId AND YEAR(l.date) = :year GROUP BY DATE(l.date)")
    List<Map<String, Object>> findUserActivityByYear(@Param("userId") Long userId, @Param("year") int year);

}
