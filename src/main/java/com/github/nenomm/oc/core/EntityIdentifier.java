package com.github.nenomm.oc.core;

import org.springframework.util.ObjectUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class EntityIdentifier implements Serializable {

	@Column(name = "id", nullable = false, unique = true)
	private String identity;

	public EntityIdentifier() {
		this.identity = UUID.randomUUID().toString();
	}

	private EntityIdentifier(String uuid) {
		this.identity = (UUID.fromString(uuid).toString());
	}

	public static EntityIdentifier fromString(String uuid) {
		return new EntityIdentifier(uuid);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj == null || !(obj.getClass().equals(this.getClass()))) {
			return false;
		}

		EntityIdentifier that = (EntityIdentifier) obj;

		return ObjectUtils.nullSafeEquals(identity, that.getIdentity());
	}

	@Override
	public int hashCode() {
		return identity.hashCode();
	}


	public String getIdentity() {
		return identity;
	}

	@Override
	public String toString() {
		return "EntityIdentifier{" +
				"identity='" + identity + '\'' +
				'}';
	}
}
