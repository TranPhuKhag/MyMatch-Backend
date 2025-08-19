package com.mymatch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1002, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    ENTITY_NOT_FOUND(1003, "Entity not found with given ID", HttpStatus.NOT_FOUND),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    USER_EXISTED(1008, "User already exists", HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(1009, "Role not found", HttpStatus.NOT_FOUND),
    EMAIL_EXISTED(1010, "Email already exists", HttpStatus.BAD_REQUEST),
    USER_HAS_BEEN_BANNED(1011, "User has been banned", HttpStatus.FORBIDDEN),
    UNIVERSITY_EXISTED(1012, "University already exists", HttpStatus.BAD_REQUEST),
    UNIVERSITY_NOT_FOUND(1013, "University not found", HttpStatus.NOT_FOUND),
    CAMPUS_EXISTED(1014, "Campus already exists", HttpStatus.BAD_REQUEST),
    CAMPUS_NOT_FOUND(1015, "Campus not found", HttpStatus.NOT_FOUND),
    STUDENT_CODE_EXISTED(1016, "Student code already exists", HttpStatus.BAD_REQUEST),
    STUDENT_CODE_NOT_FOUND(1017, "Student code not found", HttpStatus.NOT_FOUND),
    STUDENT_NOT_FOUND(1018, "Student not found", HttpStatus.NOT_FOUND),
    STUDENT_INFO_REQUIRED(1019, "Student information is required", HttpStatus.BAD_REQUEST),


    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
