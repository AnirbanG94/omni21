package com.omnichannel.app.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.admin.CompanyDocDetails;

@Repository
public interface CompanyDocDetailsRepository extends JpaRepository<CompanyDocDetails, Integer> {

}
