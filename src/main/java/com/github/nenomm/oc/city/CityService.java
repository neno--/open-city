package com.github.nenomm.oc.city;

import com.github.nenomm.oc.core.EntityIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;

	public List<City> getAllCities() {

		List<City> result = StreamSupport.stream(cityRepository.findAll().spliterator(), false).collect(Collectors.toList());

		return result;
	}

	public City getById(EntityIdentifier identifier) {

		return cityRepository.findById(identifier).get();
	}

	public City create(CityDTO cityDTO) {
		City city = new City(cityDTO.getName(), cityDTO.getDescription(), cityDTO.getPopulation());

		cityRepository.save(city);

		return city;
	}
}
