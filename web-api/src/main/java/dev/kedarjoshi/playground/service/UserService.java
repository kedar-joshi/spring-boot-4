package dev.kedarjoshi.playground.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserService
		implements UserDetailsService
{
	private static final Logger logger = LogManager.getLogger(UserService.class);

	@Override
	public UserDetails loadUserByUsername(final String username)
	{
		return null;
	}
}
