package com.a2m.gen.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.a2m.gen.dto.UserDetailsImpl;
import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.repository.TsstUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	private TsstUserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
//		Optional<TsstUser> user = userRepo.findById(username);
		TsstUser user = userRepo.findByUserUid(username);
		//String userType = user.getUserType();
		if (user == null) {
			throw new UsernameNotFoundException("User Not Found with username: " + username);
		}
		return new UserDetailsImpl(user);
	}
}
