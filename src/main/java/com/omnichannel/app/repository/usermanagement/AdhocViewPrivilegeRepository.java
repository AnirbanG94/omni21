package com.omnichannel.app.repository.usermanagement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.omnichannel.app.model.usermanagement.AdhocViewPrivilege;

@Repository
public interface AdhocViewPrivilegeRepository extends JpaRepository<AdhocViewPrivilege, Integer> {

	@Query(value = "select * from usm_adhoc_viewprivileges where user_id = ?1", nativeQuery = true)
	List<AdhocViewPrivilege> findUserByUserid(Integer id);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM usm_adhoc_viewprivileges WHERE id = ?1", nativeQuery = true)
	void deleteByrow(Integer id);

}
