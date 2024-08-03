package com.piebin.inpiece.repository;

import com.piebin.inpiece.model.domain.TeamContest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContestTeamRepository extends JpaRepository<TeamContest, Long> {
}
