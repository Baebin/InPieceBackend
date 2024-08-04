package com.piebin.inpiece.repository;

import com.piebin.inpiece.model.domain.TeamProjectRecommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamProjectRecommendRepository extends JpaRepository<TeamProjectRecommend, Long> {
}
