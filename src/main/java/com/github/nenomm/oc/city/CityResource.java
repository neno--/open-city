package com.github.nenomm.oc.city;

import com.github.nenomm.oc.core.EntityIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/cities")
public class CityResource {

	@Autowired
	private CityService cityService;

	@RequestMapping(method = RequestMethod.GET)
	public List<CityDTO> getAllCities(@RequestParam(required = false) String sortBy) {

		List<CityDTO> result;

		if (sortBy == null) {
			sortBy = "default";
		}

		switch (sortBy) {
			case "createdDate": {
				result = cityService.getAllCitiesSortByCreatedDate().stream().map(CityDTO::fromCity).collect(Collectors.toList());
				break;
			}

			case "popularity": {
				result = cityService.getAllCitiesSortByPopularity().stream().map(CityDTO::fromCity).collect(Collectors.toList());
				break;
			}

			default: {
				result = cityService.getAllCities().stream().map(CityDTO::fromCity).collect(Collectors.toList());
			}

		}

		result.forEach(cityDTO -> addSelfLink(cityDTO));

		return result;
	}

	@RequestMapping(value = "/{city-id}", method = RequestMethod.GET)
	public CityDTO getCity(@PathVariable(name = "city-id") String cityUUID) {

		EntityIdentifier cityIdentifier = EntityIdentifier.fromString(cityUUID);

		CityDTO result = CityDTO.fromCity(cityService.getById(cityIdentifier));

		return addSelfLink(result);
	}

	@RequestMapping(method = RequestMethod.POST)
	public CityDTO createCity(@Valid @RequestBody CityDTO cityDTO) {

		return addSelfLink(CityDTO.fromCity(cityService.create(cityDTO)));

	}

	private CityDTO addSelfLink(CityDTO cityDTO) {

		cityDTO.add(linkTo(methodOn(CityResource.class).getCity(cityDTO.getIdentifier().getIdentity())).withSelfRel());

		return cityDTO;
	}
}
