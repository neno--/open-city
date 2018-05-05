package com.github.nenomm.oc.user;


import com.github.nenomm.oc.city.City;
import com.github.nenomm.oc.core.AbstractEntity;
import org.springframework.util.Assert;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public class User extends AbstractEntity {

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

	public void addCity(City city) {
		favorites.add(city);
		city.addUser(this);
	}

	public void removeCity(City city) {
		favorites.remove(city);
		city.removeUser(this);
	}

	public String getEmail() {
		return email;
	}

	public Password getPassword() {
		return password;
	}
}
