package com.mymatch.repository;

import com.mymatch.entity.MemberSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberSkillRepository extends JpaRepository<MemberSkill, Long> {
}
