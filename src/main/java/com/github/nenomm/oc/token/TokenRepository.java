package com.github.nenomm.oc.token;

import com.github.nenomm.oc.core.EntityIdentifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<Token, EntityIdentifier> {

	Optional<Token> findByToken(String token);
}
