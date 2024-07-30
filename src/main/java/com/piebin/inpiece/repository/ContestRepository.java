package com.piebin.inpiece.repository;

import com.piebin.inpiece.model.domain.Account;
import com.piebin.inpiece.model.domain.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContestRepository extends JpaRepository<Contest, Long> {
    Optional<Contest> findByIdx(Long idx);

    List<Contest> findAllByOwnerOrderByIdxDesc(Account account);
}
