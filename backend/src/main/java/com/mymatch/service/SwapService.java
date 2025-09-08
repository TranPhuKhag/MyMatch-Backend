package com.mymatch.service;

import com.mymatch.dto.request.swap.SwapCreationRequest;
import com.mymatch.dto.request.swap.SwapFilterRequest;
import com.mymatch.dto.request.swap.SwapUpdateRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.swap.SwapResponse;
import com.mymatch.entity.SwapRequest;

public interface SwapService {
    void createSwap(SwapRequest swapRequestCurrent , SwapRequest existingSwapRequest);
    PageResponse<SwapResponse> getAll(SwapFilterRequest req);
    SwapResponse getById(Long id);
    SwapResponse updateDecision(Long swapId, SwapUpdateRequest req);
}
