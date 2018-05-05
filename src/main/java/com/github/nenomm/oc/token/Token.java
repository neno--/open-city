package com.github.nenomm.oc.token;

import com.github.nenomm.oc.core.AbstractEntity;
import com.github.nenomm.oc.user.User;
import org.springframework.util.Assert;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
public class Token extends AbstractEntity {

	@OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, optional = false)
	private User user;

	private String value;

	@Column(nullable = false)
	private OffsetDateTime expiresAt;

	// for hibernate
	public Token() {
	}


	public Token(User user, OffsetDateTime expiresAt) {
		Assert.notNull(user, "user must not be null");
		Assert.notNull(expiresAt, "expiresAt must not be null");

		this.user = user;
		this.expiresAt = expiresAt;
		this.value = UUID.randomUUID().toString();
	}
}
