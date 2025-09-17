package com.mymatch.service;

import com.mymatch.dto.response.skill.SkillResponse;

import java.util.List;

public interface SkillService {
    List<SkillResponse> getAllSkills();
}
