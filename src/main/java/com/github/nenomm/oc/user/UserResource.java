package com.github.nenomm.oc.user;

import com.github.nenomm.oc.core.EntityIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/users")
public class UserResource {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/{user-id}", method = RequestMethod.GET)
	public UserDTO getUser(@PathVariable(name = "user-id") String userUUID) {

		EntityIdentifier userIdentifier = EntityIdentifier.fromString(userUUID);

		UserDTO result = UserDTO.fromUser(userService.findById(userIdentifier));

		return addSelfLink(result);
	}

	@RequestMapping(method = RequestMethod.POST)
	public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) {

		return addSelfLink(UserDTO.fromUser(userService.create(userDTO)));

	}

	private UserDTO addSelfLink(UserDTO userDTO) {

		userDTO.add(linkTo(methodOn(UserResource.class).getUser(userDTO.getIdentifier().getIdentity())).withSelfRel());

		return userDTO;
	}
}
