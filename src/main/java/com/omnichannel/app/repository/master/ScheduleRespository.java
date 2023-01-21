package com.omnichannel.app.repository.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omnichannel.app.model.master.Schedule;

@Repository
public interface ScheduleRespository extends JpaRepository<Schedule, Integer> {

	Boolean existsByCode(String code);

	Schedule findByCode(String code);
}
