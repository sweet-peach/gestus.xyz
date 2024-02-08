package xyz.gestus.gestus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xyz.gestus.gestus.models.ProjectModel;

import java.util.List;

public interface ProjectRepository extends JpaRepository<ProjectModel,Long> {
    @Query("SELECT p FROM ProjectModel p JOIN p.keywords k WHERE k.id IN :keywordsId AND LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<ProjectModel> findByKeywordsIdAndNameContaining(@Param("keywordsId") List<Long> keywordsId, @Param("name") String name);
}
