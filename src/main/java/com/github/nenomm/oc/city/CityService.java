package com.github.nenomm.oc.city;

import com.github.nenomm.oc.core.EntityIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CityService {

	private Logger logger = LoggerFactory.getLogger(CityService.class);

	@Autowired
	private CityRepository cityRepository;

	public List<City> getAllCities() {

		logger.info("getting all cities");

		List<City> result = StreamSupport.stream(cityRepository.findAll().spliterator(), false).collect(Collectors.toList());

		return result;
	}

	public City getById(EntityIdentifier identifier) {

		logger.info("getting city by id");

		return cityRepository.findById(identifier).get();
	}

	public City create(CityDTO cityDTO) {
		City city = new City(cityDTO.getName(), cityDTO.getDescription(), cityDTO.getPopulation());

		cityRepository.save(city);

		return city;
	}

	public List<City> getAllCitiesSortByCreatedDate() {

		logger.info("getting all cities sort by created date");

		List<City> result = cityRepository.findAllOrderByCreatedAt();

		return result;
	}

	public List<City> getAllCitiesSortByPopularity() {

		logger.info("getting all cities sort by popularity");

		List<City> result = cityRepository.findAllOrderByPopularity();

		return result;
	}
}
