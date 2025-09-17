package com.mymatch.repository;

import com.mymatch.entity.StudentRequestSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRequestSkillRepository extends JpaRepository<StudentRequestSkill, Long> {
    boolean existsByRequest_IdAndSkill_Id(Long requestId, Long skillId);
}
