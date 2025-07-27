package dev.kedarjoshi.playground.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tokens")
public class Token
{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	@Column(name = "version", nullable = false)
	private Integer version;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Integer getVersion()
	{
		return version;
	}

	public void setVersion(final Integer version)
	{
		this.version = version;
	}

	@Override
	public String toString()
	{
		return "Token{" +
			   "id=" + id +
			   ", version=" + version +
			   '}';
	}
}
