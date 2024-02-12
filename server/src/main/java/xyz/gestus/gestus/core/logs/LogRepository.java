package xyz.gestus.gestus.core.logs;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.gestus.gestus.core.logs.Log;

import java.util.List;

public interface LogRepository extends JpaRepository<Log,Long> {
    public List<Log> getLogModelByUserId(Long userId);

    @Query("SELECT l.user, COUNT(l) as logCount FROM Log l GROUP BY l.user ORDER BY logCount DESC")
    List<Object[]> findTopUsersWithMostLogs(Pageable pageable);
}
