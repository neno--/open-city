package com.github.nenomm.oc.token;

import com.github.nenomm.oc.user.UserDTO;
import com.github.nenomm.oc.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/token")
public class TokenResource {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST)
	public TokenDTO createToken(@Valid @RequestBody UserDTO userDTO) {

		return TokenDTO.fromToken(userService.validateByEmailAndPassword(userDTO));
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void deleteToken(@Valid @RequestBody TokenDTO tokenDTO) {

		userService.deleteToken(tokenDTO);
	}
}
