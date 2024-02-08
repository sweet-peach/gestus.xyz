package xyz.gestus.gestus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xyz.gestus.gestus.models.ProjectModel;

import java.util.List;

public interface ProjectRepository extends JpaRepository<ProjectModel,Long> {
    @Query("SELECT p FROM ProjectModel p JOIN p.keywords k WHERE k.id IN :keywordsId")
    List<ProjectModel> findByKeywordsId(@Param("keywordsId") List<Long> keywordsId);
}
