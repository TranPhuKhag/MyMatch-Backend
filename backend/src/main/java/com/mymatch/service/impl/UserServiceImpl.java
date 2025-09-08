package com.mymatch.service.impl;

import com.mymatch.dto.request.student.StudentCreationRequest;
import com.mymatch.dto.request.user.UserCreationRequest;
import com.mymatch.dto.request.user.UserUpdateRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.student.StudentResponse;
import com.mymatch.dto.response.user.UserResponse;
import com.mymatch.entity.*;
import com.mymatch.enums.RoleType;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.StudentMapper;
import com.mymatch.mapper.StudentMapperImpl;
import com.mymatch.mapper.UserMapper;
import com.mymatch.repository.CampusRepository;
import com.mymatch.repository.RoleRepository;
import com.mymatch.repository.StudentRepository;
import com.mymatch.repository.UserRepository;
import com.mymatch.service.StudentService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import com.mymatch.service.UserService;
import org.springframework.transaction.annotation.Transactional;


import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
@Slf4j
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    CampusRepository campusRepository;
    StudentRepository studentRepository;
    StudentService studentService;
    StudentMapper studentMapper;
    @Override
    @Transactional
    public UserResponse createUser(UserCreationRequest request, RoleType roleType) {
        if (userRepository.existsByEmail(request.getEmail())) throw new AppException(ErrorCode.EMAIL_EXISTED);
        if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);
        Role role = roleRepository.findByName(roleType).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        Wallet wallet = Wallet
                .builder()
                .coin(0L)
                .build();

        User user = userMapper.toUser(request, role, hashedPassword);
        user.setWallet(wallet);

        if (roleType == RoleType.STUDENT) {
            Student student = studentRepository.save(studentMapper.toEntity(request.getStudentCreationRequest())
            );
            user.setStudent(student);
        }

        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse updateUser(UserUpdateRequest request, Long id) {
        return null;
    }

    @Override
    public UserResponse deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userRepository.delete(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public void unBanUser(Long userId) {
    }

    @Override
    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            var expiresAt = jwtAuth.getToken().getExpiresAt();
            if (expiresAt != null && expiresAt.isBefore(java.time.Instant.now())) {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }
        }
        String username = authentication.getName();
        User user =
                userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Set<String> permissions = user.getRole().getPermissions().stream()
                .map(Permission::getName)
                .collect(Collectors.toSet());
        Student student = user.getStudent();
        UserResponse userResponse = userMapper.toUserResponse(user);
        userResponse.setPermissions(permissions);
        return userResponse;
    }
    @Override
    public PageResponse<UserResponse> getAllUsers(int page, int size, String sort, String filter, String searchTerm) {
        return null;
    }
}
