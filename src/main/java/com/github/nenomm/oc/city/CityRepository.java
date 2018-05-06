package com.github.nenomm.oc.city;

import com.github.nenomm.oc.core.EntityIdentifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends CrudRepository<City, EntityIdentifier> {

	@Query("select city from City city order by city.createdAt asc")
	public List<City> findAllOrderByCreatedAt();

	@Query("select city from City city order by city.popularity desc")
	public List<City> findAllOrderByPopularity();

	public Optional<City> findByName(String name);
}
