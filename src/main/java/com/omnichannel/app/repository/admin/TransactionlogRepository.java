package com.omnichannel.app.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.admin.Transactionlog;

@Repository
public interface TransactionlogRepository extends JpaRepository<Transactionlog, Integer> {

	@Query(value = "SELECT * FROM adm_tran_log where timestamp between ?1 AND ?2 AND username= ?3", nativeQuery = true)
	List<Transactionlog> tranLog(String fromTimestamp, String toTimestamp, String username);

}
