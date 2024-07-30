package com.piebin.inpiece.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static final String REDIRECT_URI = "http://localhost:5173/";

    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        SecurityOAuth2User user = (SecurityOAuth2User) authentication.getPrincipal();
        if (!user.getAccount().getEmail().endsWith("@inha.edu")) {
            String uri = REDIRECT_URI + "login/failure";
            getRedirectStrategy().sendRedirect(request, response, uri);
            return;
        }

        String id = user.getAccount().getId();
        log.info("id: {}, name: {}", id, user.getName());

        String token = tokenProvider.createAccessToken(id);
        log.info("token: {}", token);

        String uri = UriComponentsBuilder.fromUriString(REDIRECT_URI + "login/redirect")
                .queryParam("token", token)
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, uri);
    }
}
