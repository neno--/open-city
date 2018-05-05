package com.github.nenomm.oc.city;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.nenomm.oc.core.EntityIdentifier;
import org.springframework.hateoas.ResourceSupport;

public class CityDTO extends ResourceSupport {

	private EntityIdentifier identifier;

	private String name;

	private String description;

	private int population;

	@JsonCreator
	public CityDTO(City city) {
		this.identifier = city.getId();
		this.name = city.getName();
		this.description = city.getDescription();
		this.population = city.getPopulation();
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

	@JsonIgnore
	public EntityIdentifier getIdentifier() {
		return identifier;
	}
}
