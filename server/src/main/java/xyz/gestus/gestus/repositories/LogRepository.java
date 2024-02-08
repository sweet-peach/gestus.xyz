package xyz.gestus.gestus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.gestus.gestus.models.LogModel;

public interface LogRepository extends JpaRepository<LogModel,Long> {}
