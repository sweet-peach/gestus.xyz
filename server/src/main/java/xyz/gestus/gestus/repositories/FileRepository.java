package xyz.gestus.gestus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.gestus.gestus.models.FileModel;

public interface FileRepository extends JpaRepository<FileModel,Long> {
    public boolean existsByPath(String path);
}
