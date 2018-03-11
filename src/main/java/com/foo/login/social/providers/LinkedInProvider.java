package com.foo.login.social.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.LinkedInProfileFull;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.foo.login.model.UserBean;

@Service
public class LinkedInProvider  {

	private static final String LINKED_IN = "linkedIn";
	
	private static final String REDIRECT_LOGIN = "redirect:/login";

  	@Autowired
    	BaseProvider socialLoginBean ;

	public String getLinkedInUserData(Model model, UserBean userForm) {

		ConnectionRepository connectionRepository = socialLoginBean.getConnectionRepository();
		if (connectionRepository.findPrimaryConnection(LinkedIn.class) == null) {
			return REDIRECT_LOGIN;
		}
		populateUserDetailsFromLinkedIn(userForm);
		
		 //Save the details in DB
		socialLoginBean.saveUserDetails(userForm);
		 
		 //Login the User
		socialLoginBean.autoLoginUser(userForm);
		
		model.addAttribute("loggedInUser",userForm);
		return "secure/user";		
	}
	
	private void populateUserDetailsFromLinkedIn(UserBean userForm) {
		LinkedIn linkedIn = socialLoginBean.getLinkedIn();
		LinkedInProfileFull linkedInUser = linkedIn.profileOperations().getUserProfileFull();
		userForm.setEmail(linkedInUser.getEmailAddress());
		userForm.setFirstName(linkedInUser.getFirstName());
		userForm.setLastName(linkedInUser.getLastName());
		userForm.setProvider(LINKED_IN);
	}

}
