package com.goodee.finals.messenger;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity @Table(name = "chat_room")
public class ChatRoomDTO {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long chatRoomNum;
	@NotBlank(message = "채팅방 이름을 입력해주세요.")
	private String chatRoomName;
	@Column(columnDefinition = "boolean default false")
	private boolean chatRoomGroup;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "chatRoomDTO", cascade = CascadeType.ALL) @JsonIgnore
	private List<ChatUserDTO> chatUserDTOs;
}
