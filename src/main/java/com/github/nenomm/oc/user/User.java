package com.github.nenomm.oc.user;


import com.github.nenomm.oc.city.City;
import com.github.nenomm.oc.core.AbstractEntity;
import com.github.nenomm.oc.core.EntityIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.Set;

@Entity
public class User extends AbstractEntity {

	@Transient
	private Logger logger = LoggerFactory.getLogger(User.class);

	@Column(nullable = false, unique = true)
	private String email;

	@Embedded
	private Password password;

	@Column(nullable = false)
	private boolean verified;

	@ManyToMany(cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE
	})

	@JoinTable(name = "favorites")
	private Set<City> favorites;

	// for hibernate
	public User() {
	}

	public User(String email, Password password) {
		Assert.hasText(email, "email must not be null or empty");
		Assert.notNull(password, "password must not be null");

		this.email = email;
		this.password = password;
	}

	public User(String email, Password password, String uuid) {
		this(email, password);
		setId(EntityIdentifier.fromString(uuid));
	}

	public void addCity(City city) {
		if (!favorites.contains(city)) {

			logger.info("adding city {} to favorites for user {}", this, city);

			favorites.add(city);

			city.addUser(this);
			city.increasePopularity();
		}
	}

	public void removeCity(City city) {
		if (favorites.contains(city)) {

			logger.info("removing city {} from favorites for user {}", this, city);

			favorites.remove(city);

			city.removeUser(this);
			city.decreasePopularity();
		}
	}

	public Set<City> getFavorites() {
		return favorites;
	}

	public String getEmail() {
		return email;
	}

	public Password getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "User{" +
				"email='" + email + '\'' +
				", password=" + password +
				", verified=" + verified +
				", favorites=" + favorites +
				'}';
	}
}
