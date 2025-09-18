package com.goodee.finals.common.attachment;

import com.goodee.finals.ride.RideDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 어트랙션 첨부파일 중간 테이블

@Getter
@Setter
@Entity
@Table(name = "ride_attach")
@IdClass(RideAttachmentDTO.PK.class)
public class RideAttachmentDTO {
	
	@Id
    @OneToOne
    @JoinColumn(name = "rideCode", insertable = false, updatable = false)
    private RideDTO rideDTO;

	@Id
    @OneToOne
    @JoinColumn(name = "attachNum", insertable = false, nullable = false)
    private AttachmentDTO attachmentDTO;
	
	@NoArgsConstructor
	@AllArgsConstructor
	@EqualsAndHashCode
	public static class PK {
		private String rideDTO;
		private Long attachmentDTO;
	}

}
