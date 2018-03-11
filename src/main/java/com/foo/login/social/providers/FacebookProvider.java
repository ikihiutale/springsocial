package com.foo.login.social.providers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.foo.login.controller.LoginController;
import com.foo.login.model.UserBean;


@Service
public class FacebookProvider  {

	private static final Logger LOG = LoggerFactory.getLogger(FacebookProvider.class);

	private static final String FACEBOOK = "facebook";
	private static final String REDIRECT_LOGIN = "redirect:/login";
	private static final String REDIRECT_LOGIN_ERR = "redirect:/login-error";

    	@Autowired
    	BaseProvider baseProvider ;
    	

	public String getFacebookUserData(Model model, UserBean userForm) {

		ConnectionRepository connectionRepository = baseProvider.getConnectionRepository();
		if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
			LOG.error("************************************************************************");
			LOG.error("*****: getFacebookUserData: Not FB data -> redirect to login");
			return REDIRECT_LOGIN;
		}
		populateUserDetailsFromFacebook(userForm);
		
		try {
			 // Save the details in DB
			 baseProvider.saveUserDetails(userForm);
			 
			 // Login the User
			 baseProvider.autoLoginUser(userForm);
			 
			 model.addAttribute("loggedInUser",userForm);
			 return "secure/user";		
		}
		catch (Exception ex) {
			return REDIRECT_LOGIN_ERR;		
		}
	}

	protected void populateUserDetailsFromFacebook(UserBean userForm) {
		Facebook facebook = baseProvider.getFacebook();
		User user = facebook.userOperations().getUserProfile();
		LOG.error("************************************************************************");
		LOG.error("*****: populateUserDetailsFromFacebook: email {}", user.getEmail());

		userForm.setEmail(user.getEmail());
		userForm.setFirstName(user.getFirstName());
		userForm.setLastName(user.getLastName());
		userForm.setProvider(FACEBOOK);
	}

	 

}
