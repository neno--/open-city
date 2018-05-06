package com.github.nenomm.oc.city;

import com.github.nenomm.oc.core.AbstractEntity;
import com.github.nenomm.oc.user.User;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class City extends AbstractEntity {

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private int population;

	@Column(nullable = false)
	private int popularity;

	@Column(nullable = false)
	private OffsetDateTime createdAt;

	@ManyToMany(mappedBy = "favorites")
	Set<User> users = new HashSet<User>();

	// for hibernate
	public City() {
	}

	// for hibernate
	public City(String name, String description, int population) {
		Assert.hasText(name, "name must not be null or empty");
		this.name = name;
		this.description = description;
		this.population = population;

		this.popularity = 0;
		this.createdAt = OffsetDateTime.now();
	}

	public void addUser(User user) {
		users.add(user);
	}

	public void removeUser(User user) {
		users.remove(user);
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getPopulation() {
		return population;
	}

	public int getPopularity() {
		return popularity;
	}

	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}

	public void increasePopularity() {
		popularity++;
	}

	public void decreasePopularity() {
		popularity--;
	}

}
