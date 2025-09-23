package com.goodee.finals.messenger;

import com.goodee.finals.staff.StaffDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity @Table(name = "chat_user")
@IdClass(ChatUserDTO.PK.class)
public class ChatUserDTO {
	private Long chatBodyNum = 0L;
	@Id @ManyToOne @JoinColumn(name = "chatRoomNum")
	private ChatRoomDTO chatRoomDTO;
	@Id @ManyToOne @JoinColumn(name = "staffCode")
	private StaffDTO staffDTO;
	@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
	public static class PK {
		private Long chatRoomDTO;
		private Integer staffDTO;
	}
}
