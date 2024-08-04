package com.piebin.inpiece.repository;

import com.piebin.inpiece.model.domain.TeamProject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamProjectRepository extends JpaRepository<TeamProject, Long> {
    Optional<TeamProject> findByIdx(Long idx);

    List<TeamProject> findAllByOrderByRegDateAsc(PageRequest pageRequest);
    List<TeamProject> findAllByOrderByRegDateDesc(PageRequest pageRequest);
    @Query(value = "select project from TeamProject project order by (select count(*) from project.recommends) asc, project.regDate asc")
    List<TeamProject> findAllByOrderByRecommendsAsc(PageRequest pageRequest);
    @Query(value = "select project from TeamProject project order by (select count(*) from project.recommends) desc, project.regDate desc")
    List<TeamProject> findAllByOrderByRecommendsDesc(PageRequest pageRequest);
    List<TeamProject> findAllByOrderByViewCountAscRegDateAsc(PageRequest pageRequest);
    List<TeamProject> findAllByOrderByViewCountDescRegDateDesc(PageRequest pageRequest);
}
