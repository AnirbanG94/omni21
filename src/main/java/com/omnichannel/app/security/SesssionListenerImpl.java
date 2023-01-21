package com.omnichannel.app.security;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.omnichannel.app.model.admin.LoginHistory;
import com.omnichannel.app.repository.admin.LoginHistoryRepository;

@Component
public class SesssionListenerImpl implements HttpSessionListener {

	@Autowired
	LoginHistoryRepository loginHistoryRepository;

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		System.out.println("sessionDestroyed invocked");
		Optional<LoginHistory> findBySessionid = loginHistoryRepository.findBySessionid(httpSessionEvent.getSession().getId());
		if(findBySessionid.isPresent()) {
			LoginHistory login = findBySessionid.get();
			login.setLogout_time(LocalDateTime.now());
			loginHistoryRepository.save(login);
		}
		else {
			System.out.println("session mismatch");
		}
	}
}
