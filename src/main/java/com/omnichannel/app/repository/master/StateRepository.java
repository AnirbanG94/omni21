package com.omnichannel.app.repository.master;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.master.State;

@Repository 
public interface StateRepository extends JpaRepository<State, Integer> {
	
    @Query("SELECT u FROM State u WHERE u.country_code = :country_code")
    public List<State> findByCountryCode(@Param("country_code") Integer id);

}
