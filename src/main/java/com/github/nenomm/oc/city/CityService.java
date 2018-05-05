package com.github.nenomm.oc.city;

import com.github.nenomm.oc.core.EntityIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;

	public List<CityDTO> getAllCities() {

		List<CityDTO> result = StreamSupport.stream(cityRepository.findAll().spliterator(), false).map(CityDTO::new)
				.collect(Collectors.toList());

		return result;
	}

	public CityDTO getById(EntityIdentifier identifier) {

		return cityRepository.findById(identifier).map(CityDTO::new).get();
	}
}
