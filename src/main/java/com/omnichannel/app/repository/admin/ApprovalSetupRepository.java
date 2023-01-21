package com.omnichannel.app.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.admin.ApprovalSetup;

@Repository
public interface ApprovalSetupRepository extends JpaRepository<ApprovalSetup, Integer> {
	
	List<ApprovalSetup> findByManuid(Integer id);

}
