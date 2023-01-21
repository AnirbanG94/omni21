package com.omnichannel.app.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.admin.document.DocumentSetupDetails;

@Repository
public interface DocumentSetupDetailsRepository extends JpaRepository<DocumentSetupDetails, Integer> {

}
