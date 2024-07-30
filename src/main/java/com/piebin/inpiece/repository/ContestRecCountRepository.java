package com.piebin.inpiece.repository;

import com.piebin.inpiece.model.domain.Account;
import com.piebin.inpiece.model.domain.ContestRecCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContestRecCountRepository extends JpaRepository<ContestRecCount, Long> {
    List<ContestRecCount> findAllByAccountOrderByIdxDesc(Account account);
}
