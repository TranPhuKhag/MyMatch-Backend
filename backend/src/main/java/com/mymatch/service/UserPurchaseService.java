package com.mymatch.service;

import com.mymatch.dto.request.purchase.UserPurchaseCreateRequest;
import com.mymatch.dto.request.purchase.UserPurchaseFilterRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.purchase.UserPurchaseResponse;


public interface UserPurchaseService {
    UserPurchaseResponse create(UserPurchaseCreateRequest request);

    PageResponse<UserPurchaseResponse> getAll(UserPurchaseFilterRequest filterRequest,
                                              int page,
                                              int size,
                                              String sortBy,
                                              String sortDirection);
}
