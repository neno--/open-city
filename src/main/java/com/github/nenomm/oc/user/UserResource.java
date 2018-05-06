package com.github.nenomm.oc.user;

import com.github.nenomm.oc.city.CityDTO;
import com.github.nenomm.oc.core.EntityIdentifier;
import com.github.nenomm.oc.security.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/users")
public class UserResource {

	private Logger logger = LoggerFactory.getLogger(UserResource.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/{user-id}", method = RequestMethod.GET)
	public UserDTO getUser(@PathVariable(name = "user-id") String userUUID) {

		EntityIdentifier userIdentifier = EntityIdentifier.fromString(userUUID);

		UserDTO result = UserDTO.fromUser(userService.findById(userIdentifier));

		return addSelfLink(result);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) {

		return addSelfLink(UserDTO.fromUser(userService.create(userDTO)));

	}

	private UserDTO addSelfLink(UserDTO userDTO) {

		userDTO.add(linkTo(methodOn(UserResource.class).getUser(userDTO.getIdentifier().getIdentity())).withSelfRel());

		return userDTO;
	}

	@RequestMapping(value = "/{user-id}/favorites", method = RequestMethod.GET)
	public List<CityDTO> getFavorites(@PathVariable(name = "user-id") String userUUID) {

		List<CityDTO> results = new ArrayList<>();

		EntityIdentifier userIdentifier = EntityIdentifier.fromString(userUUID);

		userService.getFavorites(userIdentifier).forEach(city -> results.add(CityDTO.fromCity(city)));

		return results;
	}

	@RequestMapping(value = "/{user-id}/favorites", method = RequestMethod.POST)
	public CityDTO addFavorite(@RequestBody CityDTO cityDTO, @PathVariable(name = "user-id") String userUUID, Authentication authentication) {

		CustomUserDetails customUserDetails = (CustomUserDetails) authentication;

		EntityIdentifier userIdentifier = EntityIdentifier.fromString(userUUID);

		if (userIdentifier.equals(customUserDetails.getUserId())) {

			return CityDTO.fromCity(userService.addFavorite(cityDTO, userIdentifier));

		} else {
			throw new RuntimeException("user has no privilege");
		}
	}

	@RequestMapping(value = "/{user-id}/favorites", method = RequestMethod.DELETE)
	public void removeFavorite(@RequestBody CityDTO cityDTO, @PathVariable(name = "user-id") String userUUID, Authentication authentication) {

		CustomUserDetails customUserDetails = (CustomUserDetails) authentication;

		EntityIdentifier userIdentifier = EntityIdentifier.fromString(userUUID);

		if (userIdentifier.equals(customUserDetails.getUserId())) {

			userService.removeFavorite(cityDTO, userIdentifier);

		} else {
			throw new RuntimeException("user has no privilege");
		}
	}

}
