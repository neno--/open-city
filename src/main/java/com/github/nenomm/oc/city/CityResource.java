package com.github.nenomm.oc.city;

import com.github.nenomm.oc.core.EntityIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/cities")
public class CityResource {

	@Autowired
	private CityService cityService;

	@RequestMapping(method = RequestMethod.GET)
	public List<CityDTO> getAllCities() {

		List<CityDTO> result = cityService.getAllCities();

		result.forEach(cityDTO -> cityDTO.add(linkTo(methodOn(CityResource.class).get(cityDTO.getIdentifier().getIdentity())).withSelfRel()));

		return result;
	}

	@RequestMapping(value = "/{city-id}", method = RequestMethod.GET)
	public CityDTO get(@PathVariable(name = "city-id") String cityUUID) {

		EntityIdentifier cityIdentifier = EntityIdentifier.fromString(cityUUID);
		return cityService.getById(cityIdentifier);
	}
}
