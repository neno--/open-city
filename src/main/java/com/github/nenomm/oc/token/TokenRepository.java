package com.github.nenomm.oc.token;

import com.github.nenomm.oc.core.EntityIdentifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<Token, EntityIdentifier> {

	Optional<Token> findByToken(String token);

	@Modifying
	@Query("delete from Token token where token.expiresAt < ?1")
	int deleteExpiredTokens(OffsetDateTime now);
}
