package com.github.nenomm.oc.security;


import com.github.nenomm.oc.core.EntityIdentifier;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import javax.security.auth.Subject;
import java.util.Arrays;

public class CustomUserDetails extends AbstractAuthenticationToken {
	EntityIdentifier userId;

	public CustomUserDetails(EntityIdentifier userId) {
		super(Arrays.asList());
		this.userId = userId;
	}

	public EntityIdentifier getUserId() {
		return userId;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return userId;
	}

	@Override
	public boolean implies(Subject subject) {
		return false;
	}
}
