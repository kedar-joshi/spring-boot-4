package dev.kedarjoshi.playground.repository;

import jakarta.persistence.EntityManager;

import dev.kedarjoshi.playground.model.Token;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TokenRepository
		extends BaseRepository
{
	private static final Logger logger = LogManager.getLogger(TokenRepository.class);

	@Autowired
	public TokenRepository(final EntityManager entityManager)
	{
		super(entityManager);
	}

	public Token findById(final Long id)
	{
		return db.find(Token.class, id);
	}
}
