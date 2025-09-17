package com.mymatch.service.impl;
import com.mymatch.dto.request.studentrequestskill.StudentRequestSkillBulkRequest;
import com.mymatch.dto.response.studentrequestskill.StudentRequestSkillResponse;
import com.mymatch.entity.Skill;
import com.mymatch.entity.StudentRequest;
import com.mymatch.entity.StudentRequestSkill;
import com.mymatch.entity.User;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.StudentRequestSkillMapper;
import com.mymatch.repository.SkillRepository;
import com.mymatch.repository.StudentRequestRepository;
import com.mymatch.repository.StudentRequestSkillRepository;
import com.mymatch.repository.UserRepository;
import com.mymatch.service.StudentRequestSkillService;
import com.mymatch.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class StudentRequestSkillServiceImpl implements StudentRequestSkillService {
    StudentRequestRepository studentRequestRepository;
    UserRepository userRepository;
    StudentRequestSkillMapper studentRequestSkillMapper;
    SkillRepository skillRepository;

    @Override
    public List<StudentRequestSkillResponse> replace(Long requestId, StudentRequestSkillBulkRequest req) {
        StudentRequest sr = studentRequestRepository.findById(requestId).orElseThrow(
                () -> new AppException(ErrorCode.STUDENT_REQUEST_NOT_FOUND));
        User currentUser = userRepository.findById(SecurityUtil.getCurrentUserId()).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        if (!sr.getStudent().getId().equals(currentUser.getStudent().getId())) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }
        if (req.getSkillIds() == null || req.getSkillIds().isEmpty()) {
            sr.getSkills().clear();
            studentRequestRepository.save(sr);
            return Collections.emptyList();
        }

        // Distinct không còn cần thiết vì Set tự handle, nhưng giữ để clean input
        Set<Long> incoming = new HashSet<>(req.getSkillIds());

        var skills = skillRepository.findAllById(incoming);
        if (skills.size() != incoming.size()) throw new AppException(ErrorCode.INVALID_PARAMETER);

        sr.getSkills().clear();
        for (Skill s : skills) {
            StudentRequestSkill newSkill = StudentRequestSkill.builder().request(sr).skill(s).build();
            sr.getSkills().add(newSkill);  // Set tự check duplicate dựa trên equals/hashCode
        }
        studentRequestRepository.save(sr);
        return sr.getSkills().stream().map(studentRequestSkillMapper::toResponse).toList();
    }
}
