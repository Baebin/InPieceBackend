package com.piebin.inpiece.repository;

import com.piebin.inpiece.model.domain.Contest;
import com.piebin.inpiece.model.domain.Team;
import com.piebin.inpiece.model.domain.TeamRecruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRecruitRepository extends JpaRepository<TeamRecruit, Long> {
    Optional<TeamRecruit> findByTeamAndContest(Team team, Contest contest);
}
