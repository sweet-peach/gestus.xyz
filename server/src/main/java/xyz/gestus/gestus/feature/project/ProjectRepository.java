package xyz.gestus.gestus.feature.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    @Query("SELECT p FROM Project p JOIN p.keywords k WHERE k.id IN :keywordsId AND LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Project> findByKeywordsIdAndNameContaining(@Param("keywordsId") List<Long> keywordsId, @Param("name") String name);

    @Query("SELECT p FROM Project p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Project> findByNameContaining(@Param("name") String name);
}
