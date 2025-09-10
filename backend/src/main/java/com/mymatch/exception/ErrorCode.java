package com.mymatch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // 500
    UNCATEGORIZED_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Lỗi không xác định", HttpStatus.INTERNAL_SERVER_ERROR),

    // 400 Bad Request
    INVALID_KEY(HttpStatus.BAD_REQUEST.value(), "Khóa không hợp lệ", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(HttpStatus.BAD_REQUEST.value(), "Tên đăng nhập phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST.value(), "Mật khẩu phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
    STUDENT_INFO_REQUIRED(HttpStatus.BAD_REQUEST.value(), "Thiếu thông tin sinh viên", HttpStatus.BAD_REQUEST),
    CANNOT_CREATE_CONVERSATION_WITH_YOURSELF(HttpStatus.BAD_REQUEST.value(), "Không thể tạo cuộc trò chuyện với chính bạn", HttpStatus.BAD_REQUEST),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST.value(), "Tham số không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_SWAP_DECISION(HttpStatus.BAD_REQUEST.value(), "Quyết định không hợp lệ", HttpStatus.BAD_REQUEST),
    CANNOT_SEND_EMAIL(HttpStatus.BAD_REQUEST.value(), "Không thể gửi email", HttpStatus.BAD_REQUEST),
    FILE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST.value(), "Kích thước tệp vượt quá giới hạn cho phép", HttpStatus.BAD_REQUEST),
    CANNOT_PURCHASE_OWN_MATERIAL(HttpStatus.BAD_REQUEST.value(), "Không thể mua tài liệu của chính bạn", HttpStatus.BAD_REQUEST),
    MATERIAL_ALREADY_PURCHASED(HttpStatus.BAD_REQUEST.value(), "Bạn đã mua tài liệu này", HttpStatus.BAD_REQUEST),
    MATERIAL_NOT_PURCHASED(HttpStatus.BAD_REQUEST.value(), "Bạn chưa mua tài liệu này", HttpStatus.BAD_REQUEST),
    // 401 Unauthorized
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED.value(), "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED_ACCESS_CONVERSATION(HttpStatus.UNAUTHORIZED.value(), "Bạn không có quyền truy cập cuộc trò chuyện này", HttpStatus.UNAUTHORIZED),
    // 403 Forbidden
    UNAUTHORIZED(HttpStatus.FORBIDDEN.value(), "Bạn không có quyền truy cập", HttpStatus.FORBIDDEN),
    USER_HAS_BEEN_BANNED(HttpStatus.FORBIDDEN.value(), "Tài khoản đã bị khóa", HttpStatus.FORBIDDEN),
    // 404 Not Found
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Không tìm thấy dữ liệu với ID đã cung cấp", HttpStatus.NOT_FOUND),
    COURSE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Môn học không tồn tại", HttpStatus.NOT_FOUND),
    USER_NOT_EXISTED(HttpStatus.NOT_FOUND.value(), "Người dùng không tồn tại", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Không tìm thấy vai trò", HttpStatus.NOT_FOUND),
    PERMISSION_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Không tìm thấy quyền", HttpStatus.NOT_FOUND),
    UNIVERSITY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Không tìm thấy trường đại học", HttpStatus.NOT_FOUND),
    LECTURER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Không tìm thấy giảng viên", HttpStatus.NOT_FOUND),
    CAMPUS_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Không tìm thấy cơ sở", HttpStatus.NOT_FOUND),
    STUDENT_CODE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Không tìm thấy mã sinh viên", HttpStatus.NOT_FOUND),
    STUDENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Không tìm thấy sinh viên", HttpStatus.NOT_FOUND),
    REVIEW_CRITERIA_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Không tìm thấy tiêu chí đánh giá", HttpStatus.NOT_FOUND),
    SEMESTER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Không tìm thấy học kỳ", HttpStatus.NOT_FOUND),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Không tìm thấy đánh giá", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Không tìm thấy người dùng", HttpStatus.NOT_FOUND),
    SWAPREQUEST_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Không tìm thấy yêu cầu đổi giảng viên của bạn", HttpStatus.NOT_FOUND),
    SWAP_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Không tìm thấy yêu cầu đổi giảng viên", HttpStatus.NOT_FOUND),
    CONVERSATION_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Cuộc trò chuyện không tồn tại", HttpStatus.NOT_FOUND),
    TRANSACTION_NOT_EXISTED(HttpStatus.NOT_FOUND.value(), "Giao dịch không tồn tại", HttpStatus.NOT_FOUND),
    WALLET_NOT_EXISTED(HttpStatus.NOT_FOUND.value(), "Ví không tồn tại", HttpStatus.NOT_FOUND),
    WALLET_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Không tìm thấy ví", HttpStatus.NOT_FOUND),
    MATERIAL_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Tài liệu không tồn tại", HttpStatus.NOT_FOUND),
    // 409 Conflict (đã tồn tại/trùng)
    USER_EXISTED(HttpStatus.CONFLICT.value(), "Người dùng đã tồn tại", HttpStatus.CONFLICT),
    LECTURER_EXISTED(HttpStatus.CONFLICT.value(), "Mã giảng viên đã tồn tại", HttpStatus.CONFLICT),
    EMAIL_EXISTED(HttpStatus.CONFLICT.value(), "Email đã tồn tại", HttpStatus.CONFLICT),
    UNIVERSITY_EXISTED(HttpStatus.CONFLICT.value(), "Trường đại học đã tồn tại", HttpStatus.CONFLICT),
    CAMPUS_EXISTED(HttpStatus.CONFLICT.value(), "Cơ sở đã tồn tại", HttpStatus.CONFLICT),
    COURSE_EXISTED(HttpStatus.CONFLICT.value(), "Mã môn học đã tồn tại", HttpStatus.CONFLICT),
    STUDENT_CODE_EXISTED(HttpStatus.CONFLICT.value(), "Mã sinh viên đã tồn tại", HttpStatus.CONFLICT),
    ROLE_EXISTED(HttpStatus.CONFLICT.value(), "Vai trò đã tồn tại", HttpStatus.CONFLICT),
    PERMISSION_EXISTED(HttpStatus.CONFLICT.value(), "Quyền đã tồn tại", HttpStatus.CONFLICT),
    SWAPREQUEST_ALREADY_EXISTS(HttpStatus.CONFLICT.value(), "Yêu cầu đổi giảng viên đã tồn tại", HttpStatus.CONFLICT),
    CONVERSATION_ALREADY_EXISTS(HttpStatus.CONFLICT.value(), "Cuộc trò chuyện đã tồn tại", HttpStatus.CONFLICT),
    SWAP_ALREADY_EXISTS(HttpStatus.CONFLICT.value(), "Yêu cầu đổi giảng viên đã được tạo cho cặp này", HttpStatus.CONFLICT),
    INSUFFICIENT_FUNDS(HttpStatus.BAD_REQUEST.value(), "Số dư không đủ", HttpStatus.BAD_REQUEST),
    DUPLICATE_CODE(HttpStatus.CONFLICT.value(), "Mã giao dịch đã tồn tại, vui lòng thử lại", HttpStatus.CONFLICT),;
    SWAP_ALREADY_EXISTS(HttpStatus.CONFLICT.value(), "Yêu cầu đổi giảng viên đã được tạo cho cặp này", HttpStatus.CONFLICT), INSUFFICIENT_FUNDS(HttpStatus.BAD_REQUEST.value(), "Số dư không đủ", HttpStatus.BAD_REQUEST);
    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
