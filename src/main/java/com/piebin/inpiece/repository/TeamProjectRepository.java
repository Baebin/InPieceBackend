package com.piebin.inpiece.repository;

import com.piebin.inpiece.model.domain.TeamProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamProjectRepository extends JpaRepository<TeamProject, Long> {
    Optional<TeamProject> findByIdx(Long idx);
}
