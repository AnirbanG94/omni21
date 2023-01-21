package com.omnichannel.app.MvcConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.UrlPathHelper;

class LoginPageInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        UrlPathHelper urlPathHelper = new UrlPathHelper();
        if ("/login".equals(urlPathHelper.getLookupPathForRequest(request)) && isAuthenticated()) {

            String encodedRedirectURL = response.encodeRedirectURL(
              request.getContextPath() + "/home");
            response.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
            response.setHeader("Location", encodedRedirectURL);

            return false;
        } else if("/".equals(urlPathHelper.getLookupPathForRequest(request)) && isAuthenticated()){

            String encodedRedirectURL = response.encodeRedirectURL(
              request.getContextPath() + "/home");
            response.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
            response.setHeader("Location", encodedRedirectURL);

            return false;

        }
        
        else {
            return true;
        }
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}

