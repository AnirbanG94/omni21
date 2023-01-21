package com.omnichannel.app.repository.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.master.Reason;

@Repository
public interface ReasonRepository extends JpaRepository<Reason, Integer> {

}
