package com.github.nenomm.oc.token;

import com.github.nenomm.oc.core.AbstractEntity;
import com.github.nenomm.oc.user.User;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
public class Token extends AbstractEntity {

	// one user can be logged in from different devices - allow multiple tokens for this case.
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private User user;

	private String token;

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
		this.token = UUID.randomUUID().toString();
	}

	public Token(User user, OffsetDateTime expiresAt, String token) {
		Assert.notNull(user, "user must not be null");
		Assert.notNull(expiresAt, "expiresAt must not be null");

		this.user = user;
		this.expiresAt = expiresAt;
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public User getUser() {
		return user;
	}

	public boolean isExpired() {
		return expiresAt.isBefore(OffsetDateTime.now());
	}

	@Override
	public String toString() {
		return "Token{" +
				"token='" + token + '\'' +
				", expiresAt=" + expiresAt +
				'}';
	}
}
