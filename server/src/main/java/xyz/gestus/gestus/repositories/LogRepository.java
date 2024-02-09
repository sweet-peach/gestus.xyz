package xyz.gestus.gestus.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.gestus.gestus.models.LogModel;

import java.util.List;

public interface LogRepository extends JpaRepository<LogModel,Long> {
    public List<LogModel> getLogModelByUserId(Long userId);

    @Query("SELECT l.user, COUNT(l) as logCount FROM LogModel l GROUP BY l.user ORDER BY logCount DESC")
    List<Object[]> findTopUsersWithMostLogs(Pageable pageable);
}
