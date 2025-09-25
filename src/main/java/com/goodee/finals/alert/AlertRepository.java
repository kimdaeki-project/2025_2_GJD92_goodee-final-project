package com.goodee.finals.alert;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends JpaRepository<AlertDTO, Long> {

	List<AlertDTO> findAllByStaffDTOStaffCodeOrderByAlertNumAsc(Integer staffCode);

}
