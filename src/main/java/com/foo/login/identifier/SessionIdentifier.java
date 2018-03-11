package com.foo.login.identifier;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.foo.login.controller.LoginController;


@Configuration
public class SessionIdentifier extends SocialConfigurerAdapter
{
	
	private static final Logger LOG = LoggerFactory.getLogger(SessionIdentifier.class);


    @Override
    public UserIdSource getUserIdSource() {
        return new SessionIdUserIdSource();
    }

    private static final class SessionIdUserIdSource implements UserIdSource {
    	@Override
        public String getUserId() {
            RequestAttributes request = RequestContextHolder.currentRequestAttributes();
            String uuid = (String) request.getAttribute("_socialUserUUID", RequestAttributes.SCOPE_SESSION);
            if (uuid == null) {
                uuid = UUID.randomUUID().toString();
                request.setAttribute("_socialUserUUID", uuid, RequestAttributes.SCOPE_SESSION);
            }
            LOG.error("SessionIdUserIdSource#getUserId: uuid=" + uuid);
            return uuid;
        }
    }
}