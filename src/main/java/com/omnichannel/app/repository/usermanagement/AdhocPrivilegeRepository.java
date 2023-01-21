package com.omnichannel.app.repository.usermanagement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.omnichannel.app.model.usermanagement.AdhocPrivilege;

@Repository
public interface AdhocPrivilegeRepository extends JpaRepository<AdhocPrivilege, Integer> {
	
	@Query(value = "select * from usm_adhoc_privileges where user_id = ?1", nativeQuery = true)
	List<AdhocPrivilege> findUserByUserid(Integer id);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM usm_adhoc_privileges WHERE id = ?1", nativeQuery = true)
	void deleteByrow(Integer id);
}
