package com.piebin.inpiece.repository;

import com.piebin.inpiece.model.domain.Account;
import com.piebin.inpiece.model.domain.Contest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContestRepository extends JpaRepository<Contest, Long> {
    Optional<Contest> findByIdx(Long idx);

    List<Contest> findAllByOwnerOrderByIdxDesc(Account account);

    List<Contest> findAllByOrderByRegDateAsc(PageRequest pageRequest);
    List<Contest> findAllByOrderByRegDateDesc(PageRequest pageRequest);
    @Query(value = "select contest from Contest contest order by (select count(*) from contest.recommends) asc, contest.regDate asc")
    List<Contest> findAllByOrderByRecommendsAsc(PageRequest pageRequest);
    @Query(value = "select contest from Contest contest order by (select count(*) from contest.recommends) desc, contest.regDate desc")
    List<Contest> findAllByOrderByRecommendsDesc(PageRequest pageRequest);
}
