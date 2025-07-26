package dev.kedarjoshi.playground.repository;

import jakarta.persistence.EntityManager;

public class BaseRepository
{
	protected final EntityManager db;

	public BaseRepository(final EntityManager entityManager)
	{
		this.db = entityManager;
	}
}
