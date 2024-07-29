package com.piebin.inpiece.repository;

import com.piebin.inpiece.model.domain.Account;
import com.piebin.inpiece.model.domain.Team;
import com.piebin.inpiece.model.domain.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    void deleteByTeamAndAccount(Team team, Account account);
}
