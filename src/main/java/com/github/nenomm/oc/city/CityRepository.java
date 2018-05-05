package com.github.nenomm.oc.city;

import com.github.nenomm.oc.core.EntityIdentifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends CrudRepository<City, EntityIdentifier> {

	@Query("select city from City city order by city.createdAt asc")
	public List<City> findAllOrderByCreatedAt();

	@Query("select city from City city order by city.favoriteCount desc")
	public List<City> findAllOrderByFavoriteCount();
}
