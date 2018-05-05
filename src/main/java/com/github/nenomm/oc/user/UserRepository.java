package com.github.nenomm.oc.user;

import com.github.nenomm.oc.city.City;
import com.github.nenomm.oc.core.EntityIdentifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, EntityIdentifier> {

	public Optional<User> findById(EntityIdentifier identifier);

}
