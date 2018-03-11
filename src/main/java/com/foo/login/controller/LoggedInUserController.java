package com.foo.login.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
 
import com.foo.login.model.UserBean;
import com.foo.login.repository.UserRepository;
 
@Controller
public class LoggedInUserController {

	private static final Logger LOG = LoggerFactory.getLogger(LoggedInUserController.class);
	
    @Autowired
    private UserRepository userRepository;
 
	@ModelAttribute("loggedInUser")
    public void secure(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserBean user = userRepository.findByEmail(auth.getName());
		LOG.error("************************************************************************");
		LOG.error("*****: secure: auth name {}, {}", (auth != null ? auth.getName() : "null"), (user != null ? user.getEmail() : "null"));
		model.addAttribute("loggedInUser", user);
    }
 
    @GetMapping("/secure/user")
    public String securePage() {
    	return "secure/user";
    }
 
}