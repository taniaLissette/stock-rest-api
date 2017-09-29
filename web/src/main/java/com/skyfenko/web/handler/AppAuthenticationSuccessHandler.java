package com.skyfenko.web.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Override default auth success handler to avoid well-known too many redirects problem
 *
 * @author Stanislav Kyfenko
 */
@Slf4j
public class AppAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.trace("authentication was successfull. moving to next page...");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
