package com.github.nenomm.oc.city;

import com.github.nenomm.oc.core.AbstractEntity;
import com.github.nenomm.oc.user.User;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.time.OffsetDateTime;
import java.util.Set;

@Entity
public class City extends AbstractEntity {

	@Column(nullable = false, unique = true)
	private String name;

	@Column
	private String description;

	@Column(nullable = false)
	private int population;

	@Column(nullable = false)
	private int favoriteCount;

	@Column(nullable = false)
	private OffsetDateTime createdAt;

	@ManyToMany(mappedBy = "favorites")
	Set<User> users;

	// for hibernate
	public City() {
	}

	// for hibernate
	public City(String name, String description, int population) {
		Assert.hasText(name, "name must not be null or empty");
		this.name = name;
		this.description = description;
		this.population = population;

		this.favoriteCount = 0;
		this.createdAt = OffsetDateTime.now();
	}

	public void addUser(User user) {
		users.add(user);
	}

	public void removeUser(User user) {
		users.remove(user);
	}
}
