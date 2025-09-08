package com.mymatch.service;

import com.mymatch.dto.request.swaprequest.SwapRequestCreationRequest;
import com.mymatch.dto.request.swaprequest.SwapRequestFilterRequest;
import com.mymatch.dto.request.swaprequest.SwapRequestUpdateRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.swaprequest.SwapRequestResponse;
import org.springframework.security.access.prepost.PreAuthorize;

public interface SwapRequestService {
    SwapRequestResponse createSwapRequest(SwapRequestCreationRequest request);
    SwapRequestResponse updateSwapRequest(Long id, SwapRequestUpdateRequest request);
    SwapRequestResponse getSwapRequestById(Long id);
    void deleteSwapRequest(Long id);
    PageResponse<SwapRequestResponse> getAllSwapRequests(SwapRequestFilterRequest req);
    SwapRequestResponse cancelSwapRequest(Long id);
}