package com.mymatch.service;

import com.mymatch.dto.request.auth.AuthenticationRequest;
import com.mymatch.dto.request.auth.IntrospectRequest;
import com.mymatch.dto.request.auth.LogoutRequest;
import com.mymatch.dto.request.auth.RefreshRequest;
import com.mymatch.dto.response.auth.AuthenticationResponse;
import com.mymatch.dto.response.auth.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    /**
     * Validate and introspect a given JWT token.
     * @param request payload containing the token to validate
     * @return response indicating token validity
     */
    IntrospectResponse introspect(IntrospectRequest request) throws ParseException;

    /**
     * Authenticate user credentials and issue a new JWT.
     * @param request credentials (username and password)
     * @return authentication response with the issued token
     */
    AuthenticationResponse login(AuthenticationRequest request);

    /**
     * Invalidate a refresh or access token (logout operation).
     * @param request containing the token to revoke
     * @throws ParseException if token parsing fails
     * @throws JOSEException if token verification fails
     */
    void logout(LogoutRequest request) throws ParseException, JOSEException;

    /**
     * Refresh an existing JWT using a valid refresh token.
     * @param request containing the refresh token
     * @return new authentication response with refreshed tokens
     * @throws ParseException if token parsing fails
     * @throws JOSEException if token verification fails
     */
    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
    AuthenticationResponse outboundAuthenticate(String code, String redirectUri);
}
