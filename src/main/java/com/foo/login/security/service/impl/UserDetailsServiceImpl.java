package com.foo.login.security.service.impl;

import java.util.HashSet;
import java.util.Set;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import com.foo.login.model.UserBean;
import com.foo.login.repository.UserRepository;
 
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
 
	@Autowired
	private UserRepository userRepository;
 
 
	/**
	 * In order to have authentication by a DAO class, we need to implement the UserDetailsService interface. 
	 * This class is used to load user information when the user uses form login- loadUserByUsername() method 
	 * which is used to validate the user. The implementation is handling:
	 * - find username in DB, if not found throw an exception
     * - if the user is found, login user and return the User object of type org.springframework.security.core.userdetails.User, 
     *   spring will automatically update the Security context for us. 
     *   
     * Note the com.foo.login.security.SecurityConfig.configureGlobal(AuthenticationManagerBuilder)
	 */
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
 
		UserBean user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("No user found with email: " + email);
		}
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("LOGGED_USER"));
		return new User(user.getEmail(), user.getPassword(), grantedAuthorities);
	}
}