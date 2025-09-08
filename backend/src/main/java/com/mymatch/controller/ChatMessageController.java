package com.mymatch.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mymatch.dto.request.chatmessage.ChatMessageCreationRequest;
import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.ChatMessageResponse;
import com.mymatch.service.ChatMessageService;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatMessageController {
    ChatMessageService chatMessageService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ApiResponse<ChatMessageResponse> createChatMessage(
            @RequestBody
            @Valid
            ChatMessageCreationRequest request
    ) throws JsonProcessingException {
     return ApiResponse.<ChatMessageResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Tạo tin nhắn thành công")
                .result(chatMessageService.createChatMessage(request))
                .build();
    }
    @GetMapping
    ApiResponse<List<ChatMessageResponse>> getMessages(
            @RequestParam("conversationId") Long conversationId
    ) {
        return ApiResponse.<List<ChatMessageResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy tin nhắn thành công")
                .result(chatMessageService.getMessages(conversationId))
                .build();
    }
}
