package com.goodee.finals.drive;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentDTO, Long>{
	public List<DocumentDTO> findAllByDriveDTO_DriveNum(Long driveNum);
	public DocumentDTO findByattachmentDTO_AttachNum(Long attachNum);
}
