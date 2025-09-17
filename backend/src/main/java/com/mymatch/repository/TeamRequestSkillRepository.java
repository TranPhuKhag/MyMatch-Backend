package com.mymatch.repository;

import com.mymatch.entity.TeamRequestSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRequestSkillRepository extends JpaRepository<TeamRequestSkill, Long> {
}
