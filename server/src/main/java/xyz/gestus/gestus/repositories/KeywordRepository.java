package xyz.gestus.gestus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.gestus.gestus.models.KeywordModel;

public interface KeywordRepository extends JpaRepository<KeywordModel,Long> {

}
