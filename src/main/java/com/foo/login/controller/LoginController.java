package com.foo.login.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.foo.login.autologin.Autologin;
import com.foo.login.model.UserBean;
import com.foo.login.repository.UserRepository;
import com.foo.login.social.providers.FacebookProvider;
import com.foo.login.social.providers.GoogleProvider;
import com.foo.login.social.providers.LinkedInProvider;

@Controller
public class LoginController {
	
	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

	@Autowired 
	FacebookProvider facebookProvider;
	
	@Autowired 
	GoogleProvider googleProvider;

	@Autowired 
	LinkedInProvider linkedInProvider;
	
    @Autowired
    private UserRepository userRepository;
 
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
 
    @Autowired
    private Autologin autologin;

	@RequestMapping(value = "/facebook", method = RequestMethod.GET)
	public String loginToFacebook(Model model) {
		return facebookProvider.getFacebookUserData(model, new UserBean());
	}

	@RequestMapping(value = "/google", method = RequestMethod.GET)
	public String loginToGoogle(Model model) {
		return googleProvider.getGoogleUserData(model, new UserBean());
	}

	@RequestMapping(value = "/linkedin", method = RequestMethod.GET)
	public String helloFacebook(Model model) {
		return linkedInProvider.getLinkedInUserData(model, new UserBean());
	}

	@RequestMapping(value = { "/","/login" })
	public String login() {
		return "login";
	}
	
	@GetMapping("/registration")
	public String showRegistration(UserBean userBean) {
		return "registration";
	}
	
	@ModelAttribute("loggedInUser")
    public void secure(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserBean user = userRepository.findByEmail(auth.getName());
		LOG.error("************************************************************************");
		LOG.error("*****: secure: auth name {}, {}", (auth != null ? auth.getName() : "null"), (user != null ? user.getEmail() : "null"));
		model.addAttribute("loggedInUser", user);
    }

    @PostMapping("/registration")
    public String registerUser(HttpServletResponse httpServletResponse, Model model, @Valid UserBean userBean, BindingResult bindingResult) {
    	if (bindingResult.hasErrors()) {
    		return "registration";
    	}
    	userBean.setProvider("REGISTRATION");
    	// Save the details in DB
    	if (StringUtils.isNotEmpty(userBean.getPassword())) {
    		userBean.setPassword(bCryptPasswordEncoder.encode(userBean.getPassword()));
    	}
    	userRepository.save(userBean);
 
    	autologin.setSecuritycontext(userBean);
 
    	model.addAttribute("loggedInUser", userBean);
    	return "secure/user";
    }
 
    /** If we can't find a user/email combination */
    @RequestMapping("/login-error")
    public String loginError(Model model) {
    	model.addAttribute("loginError", true);
    	return "login";
    }

}
