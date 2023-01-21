package com.omnichannel.app.repository.master;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.master.City;

@Repository
public interface CityRepository extends JpaRepository<City,Integer> {
    
	List<City> findByPincode(Integer pincode);

	Boolean existsByPincode(Integer pincode);

	@Query(value = "SELECT * FROM mst_city WHERE pincode LIKE %?1% OR area LIKE %?1% OR city LIKE %?1% OR district LIKE %?1% OR state LIKE %?1% ",nativeQuery = true)
	List<City> searchByKeyword(String keyword);
	
}
