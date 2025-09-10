package com.mymatch.controller;

import com.mymatch.dto.request.conversation.ConversationCreationRequest;
import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.conversation.ConversationResponse;
import com.mymatch.service.ConversationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversations")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConversationController {
    ConversationService conversationService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ConversationResponse> createLecturer(
            @RequestBody
            @Valid
            ConversationCreationRequest req) {
        return ApiResponse.<ConversationResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Tạo cuộc trò chuyện thành công")
                .result(conversationService.createConversation(req))
                .build();
    }
    @GetMapping("/my-conversations")
    public ApiResponse<List<ConversationResponse>> myConversations() {
        return ApiResponse.<List<ConversationResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách cuộc trò chuyện thành công")
                .result(conversationService.myConversations())
                .build();
    }
}
