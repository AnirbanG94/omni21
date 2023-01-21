package com.omnichannel.app.service.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.omnichannel.app.model.usermanagement.MyUserDetails;
import com.omnichannel.app.model.usermanagement.User;
import com.omnichannel.app.repository.usermanagement.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				Optional<User> findByUsername = userRepository.findByUsername(username);
				
				System.out.println(findByUsername.isPresent());

				if (findByUsername.isEmpty()) {
					System.out.println("Could not find user");
					throw new UsernameNotFoundException("Could not find user");
				}
				else {
					
					return new MyUserDetails(findByUsername.get());
					
				}
				
				
	}

}
