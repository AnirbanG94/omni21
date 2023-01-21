package com.omnichannel.app.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.admin.FinancialYear;

@Repository
public interface FinancialYearRepository extends JpaRepository<FinancialYear, Integer> {

}
