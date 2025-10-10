package com.mymatch.repository;

import com.mymatch.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    boolean existsByTeam_IdAndMember_Id(Long teamId, Long memberId);
    boolean existsByMember_Id(Long memberId);

}
