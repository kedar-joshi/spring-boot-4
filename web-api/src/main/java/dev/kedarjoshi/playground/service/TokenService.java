package dev.kedarjoshi.playground.service;

import dev.kedarjoshi.playground.model.Token;
import dev.kedarjoshi.playground.repository.TokenRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TokenService
{
	private static final Logger logger = LogManager.getLogger(TokenService.class);
	private final TokenRepository repository;

	@Autowired
	public TokenService(final TokenRepository repository)
	{
		this.repository = repository;
	}

	@Scheduled(fixedRate = 10000)
	public void generateToken()
	{
		final Token token = repository.findById(1L);

		logger.info("Found token : {}", token);
	}

}
