package com.piebin.inpiece.repository;

import com.piebin.inpiece.model.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    boolean existsByPathAndName(String path, String name);
    boolean existsByPathAndRegDateBetweenOrderByIdxDesc(String path, LocalDateTime start, LocalDateTime end);

    Optional<Image> findByPathAndName(String path, String name);
}
