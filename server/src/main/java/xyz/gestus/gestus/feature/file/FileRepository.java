package xyz.gestus.gestus.feature.file;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File,Long> {
    public boolean existsByPath(String path);
    public File findByIdAndProjectId(Long id, Long projectId);
    public List<File> findAllByProjectId(Long projectId);
    public List<File> findAllByParentIdAndProjectId(Long parentId, Long projectId);
    public List<File> findByProjectId(Long projectId);
}
