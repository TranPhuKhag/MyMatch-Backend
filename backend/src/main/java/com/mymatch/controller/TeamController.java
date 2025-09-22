package com.mymatch.controller;

import com.mymatch.dto.request.team.TeamCreationRequest;
import com.mymatch.dto.request.team.TeamFilterRequest;
import com.mymatch.dto.request.team.TeamUpdateRequest;
import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.team.TeamResponse;
import com.mymatch.service.TeamService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TeamController {
    TeamService teamService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<TeamResponse> create(@Valid @RequestBody TeamCreationRequest req) {
        return ApiResponse.<TeamResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Tạo team thành công")
                .result(teamService.createTeam(req))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<TeamResponse> update(@PathVariable Long id,
                                            @Valid @RequestBody TeamUpdateRequest req) {
        return ApiResponse.<TeamResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật team thành công")
                .result(teamService.updateTeam(id, req))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<TeamResponse> getById(@PathVariable Long id) {
        return ApiResponse.<TeamResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin team thành công")
                .result(teamService.getTeamById(id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Xoá team thành công")
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<TeamResponse>> getAll(@ModelAttribute TeamFilterRequest filter) {
        return ApiResponse.<PageResponse<TeamResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách team thành công")
                .result(teamService.getAllTeams(filter))
                .build();
    }
}
