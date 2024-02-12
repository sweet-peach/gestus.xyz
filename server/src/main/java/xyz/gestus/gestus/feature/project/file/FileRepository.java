package xyz.gestus.gestus.feature.project.file;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.gestus.gestus.feature.project.file.File;

import java.util.List;

public interface FileRepository extends JpaRepository<File,Long> {
    public boolean existsByPath(String path);
    public File findByIdAndProjectId(Long id, Long projectId);
    public List<File> findAllByParentIdAndProjectId(Long parentId, Long projectId);
    List<File> findByParentId(Long parentId);
    public List<File> findByProjectId(Long projectId);
}
