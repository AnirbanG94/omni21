package com.omnichannel.app.security;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import com.omnichannel.app.model.admin.LoginHistory;
import com.omnichannel.app.repository.admin.LoginHistoryRepository;

@Component
public class MySuccessHandler implements AuthenticationSuccessHandler {
	
	private static Logger logger = LoggerFactory.getLogger(MySuccessHandler.class);
	
	@Autowired
	LoginHistoryRepository loginHistoryRepository;


	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String redirectUrl = null;
		if (auth != null) {
			redirectUrl = "/home";
			logger.info("The user " + auth.getName() + " has logged in.");
			String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
			LoginHistory login = new LoginHistory();
			login.setUsername(auth.getName());
			login.setSessionid(sessionId);
			login.setLogin_time(LocalDateTime.now());
			loginHistoryRepository.save(login);
		
        }
		else {
			redirectUrl = "/403";
			logger.info("Not Authorize");
		}
		
		new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
		
	}

}
