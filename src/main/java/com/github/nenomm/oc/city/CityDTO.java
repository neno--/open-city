package com.github.nenomm.oc.city;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.nenomm.oc.core.EntityIdentifier;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class CityDTO extends ResourceSupport {

	private EntityIdentifier identifier;

	@NotBlank(message = "name is mandatory")
	private String name;

	@NotBlank(message = "description is mandatory")
	private String description;

	@Min(value = 0, message = "population must be greater or equal to 0")
	private int population;

	@JsonCreator
	public CityDTO() {
	}

	public CityDTO(String name, String description, int population) {
		this.name = name;
		this.description = description;
		this.population = population;
	}

	private CityDTO(City city) {
		this.identifier = city.getId();
		this.name = city.getName();
		this.description = city.getDescription();
		this.population = city.getPopulation();
	}

	public static CityDTO fromCity(City city) {
		return new CityDTO(city);
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
