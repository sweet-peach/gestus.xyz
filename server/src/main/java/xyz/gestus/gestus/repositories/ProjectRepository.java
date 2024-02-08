package xyz.gestus.gestus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.gestus.gestus.models.ProjectModel;

public interface ProjectRepository extends JpaRepository<ProjectModel,Long> {
}
