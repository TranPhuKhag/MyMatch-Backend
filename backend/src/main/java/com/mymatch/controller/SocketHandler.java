package com.mymatch.controller;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.mymatch.dto.request.auth.IntrospectRequest;
import com.mymatch.dto.response.auth.IntrospectResponse;
import com.mymatch.entity.Student;
import com.mymatch.entity.WebSocketSession;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.repository.StudentRepository;
import com.mymatch.repository.UserRepository;
import com.mymatch.service.AuthenticationService;
import com.mymatch.service.WebSocketSessionService;
import com.mymatch.utils.SecurityUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Instant;

import static java.lang.Long.parseLong;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class SocketHandler {
    SocketIOServer server;
    AuthenticationService authenticationService;
    WebSocketSessionService webSocketSessionService;
    private final UserRepository userRepository;

//    @PostConstruct
//    void init() {
//        server.addListeners(this); // đăng ký các @OnConnect/@OnDisconnect trên class này
//        log.info("Socket.IO listening on {}", server.getConfiguration().getPort());
//    }

    @OnConnect
    public void clientConnected(SocketIOClient client) throws ParseException {
        log.info("A client connected.{}", client.getSessionId());

        // Get Token from request param
        String token = client.getHandshakeData().getSingleUrlParam("token");

        //Verify token
        IntrospectResponse introspectResponse = authenticationService.introspect(IntrospectRequest.builder()
                .token(token)
                .build());

        if (introspectResponse.isValid()){
            log.info("Client connected: {}", client.getSessionId());
            // Persist webSocketSession
            WebSocketSession webSocketSession = WebSocketSession.builder()
                    .socketSessionId(client.getSessionId().toString())
                    .studentId(introspectResponse.getStudentId())
                    .createdAt(Instant.now())
                    .build();
            webSocketSession = webSocketSessionService.create(webSocketSession);
            log.info("WebSocket session created : {}", webSocketSession);
            log.info("Student ID from token: {}", introspectResponse.getStudentId());
        } else {
            log.warn("Client {} failed to authenticate. Disconnecting...", client.getSessionId());
            client.disconnect();
        }
    }
    @OnDisconnect
    public void clientDisconnected(SocketIOClient client) {
        log.info("A client disconnected.{}", client.getSessionId());
        webSocketSessionService.deleteBySessionId(client.getSessionId().toString());
    }
    @PostConstruct       // Khởi động server sau khi bean được khởi tạo
    public void startServer() {
        server.start();
        server.addListeners(this);
        log.info("Socket.IO server started on port {}", server.getConfiguration().getPort());
    }
    @PreDestroy
    public void stopServer() {
        server.stop();
        log.info("Socket.IO server stopped.");
    }

}

