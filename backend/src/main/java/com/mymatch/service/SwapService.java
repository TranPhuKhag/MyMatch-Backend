package com.mymatch.service;

import com.mymatch.dto.request.swap.SwapCreationRequest;
import com.mymatch.dto.request.swap.SwapUpdateRequest;
import com.mymatch.dto.response.swap.SwapResponse;

public interface SwapService {
    SwapResponse createSwap(SwapCreationRequest request);
    SwapResponse updateStatus(Long id, SwapUpdateRequest request);
    SwapResponse getById(Long id);
    void delete(Long id);
}
