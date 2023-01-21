package com.omnichannel.app.repository.admin;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.admin.LoginHistory;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Integer> {

	Optional<LoginHistory> findBySessionid(String sessionid);

	@Query(value = "SELECT * FROM adm_login_history where cast(login_dt_time as date)= ?1", nativeQuery = true)
	List<LoginHistory> getTodayLogin(LocalDate date);

	@Query(value = "SELECT * FROM adm_login_history where user_id= ?1", nativeQuery = true)
	List<LoginHistory> getTodayLoginByUser(String username);

	@Query(value = "SELECT * FROM adm_login_history where cast(login_dt_time as date) between ?1 AND ?2 ", nativeQuery = true)
	List<LoginHistory> getTodayLoginByDate(String fromDate, String todate);

	@Query(value = "SELECT * FROM adm_login_history where cast(login_dt_time as date) between ?1 AND ?2 AND user_id= ?3", nativeQuery = true)
	List<LoginHistory> getTodayLoginByDateAndUser(String fromDate, String todate, String username);

}
