package xyz.gestus.gestus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.gestus.gestus.models.FileModel;

import java.util.List;

public interface FileRepository extends JpaRepository<FileModel,Long> {
    public boolean existsByPath(String path);
    public FileModel findByIdAndProjectId(Long parentId, Long projectId);
    public List<FileModel> findAllByParentIdAndProjectId(Long parentId, Long projectId);
    List<FileModel> findByParentId(Long parentId);
    public List<FileModel> findByProjectId(Long projectId);
}
