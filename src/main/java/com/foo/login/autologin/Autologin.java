package com.foo.login.autologin;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.foo.login.controller.LoginController;
import com.foo.login.model.UserBean;
 
@Service
public class Autologin {
	private static final Logger LOG = LoggerFactory.getLogger(Autologin.class);
 
	public void setSecurityContext(UserBean userForm) {
		LOG.error("setSecuritycontext: " + userForm.toString());
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority(userForm.getProvider().toUpperCase()));
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				userForm.getEmail(), userForm.getPassword(), grantedAuthorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
 