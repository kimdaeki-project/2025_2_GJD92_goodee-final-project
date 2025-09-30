package com.goodee.finals.messenger;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "chat_body")
public class MessengerTestDTO {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long chatBodyNum;
	private Long chatRoomNum;
	private Integer staffCode;
	@Column(columnDefinition = "LONGTEXT")
	private String chatBodyContent;
	private LocalDateTime chatBodyDtm = LocalDateTime.now();
	@Column(columnDefinition = "boolean default false")
	private boolean chatBodyDelete;
	private String chatBodyType;
	
	@Transient
	private MessengerType type;
	@Transient
	private String staffName;
	@Transient
	private String chatDate;
	@Transient 
	private String chatTime;
}
