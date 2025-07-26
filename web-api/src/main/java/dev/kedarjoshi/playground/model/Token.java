package dev.kedarjoshi.playground.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tokens")
public class Token
{
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	@Override
	public String toString()
	{
		return "Token{" +
			   "id=" + id +
			   '}';
	}
}
