package com.mymatch.service.impl;

import com.mymatch.dto.response.skill.SkillResponse;
import com.mymatch.mapper.SkillMapper;
import com.mymatch.repository.SkillRepository;
import com.mymatch.service.SkillService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SkillServiceImpl implements SkillService {
    SkillRepository skillRepository;
    SkillMapper skillMapper;

    @Override
    public List<SkillResponse> getAllSkills() {
        return skillRepository.findAll().stream().map(skillMapper::toResponse).toList();
    }
}
