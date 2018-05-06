package com.github.nenomm.oc.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.nenomm.oc.core.EntityIdentifier;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

public class UserDTO extends ResourceSupport {

	private EntityIdentifier identifier;

	@Email
	private String email;

	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$", message = "4 to 8 character password requiring numbers and both lowercase and uppercase letters")
	private String password;

	public String getEmail() {
		return email;
	}

	@JsonCreator
	public UserDTO() {
	}

	private UserDTO(User user) {
		this.identifier = user.getId();
		this.email = user.getEmail();
		this.password = user.getPassword().toString();
	}

	public static UserDTO fromUser(User user) {
		return new UserDTO(user);
	}

	public String getPassword() {
		return password;
	}

	@JsonIgnore
	public EntityIdentifier getIdentifier() {
		return identifier;
	}
}
