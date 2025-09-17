package com.goodee.finals.drive;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriveShareRepository extends JpaRepository<DriveShareDTO, DriveShareDTO.PK> {

}
