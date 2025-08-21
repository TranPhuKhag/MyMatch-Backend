package com.mymatch.repository.httpClient;

import com.mymatch.dto.response.auth.OutboundUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "outbound-user-client", url = "https://www.googleapis.com")
public interface OutboundUserClient {
    @GetMapping(value = "/oauth2/v1/userinfo", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    OutboundUserResponse getUserInfo(
            @RequestParam("alt") String alt,
            @RequestParam("access_token") String accessToken);
}
