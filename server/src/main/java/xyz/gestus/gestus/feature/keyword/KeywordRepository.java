package xyz.gestus.gestus.feature.keyword;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword,Long> {
    List<Keyword> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Keyword findByName(String name);
    List<Keyword> findAllByNameIn(List<String> name);
}
