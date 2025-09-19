package com.goodee.finals.messenger;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessengerRepository extends JpaRepository<ChatRoomDTO, Long> {

	@Query("SELECT cr " +
		   "FROM ChatRoomDTO cr " +
		   "JOIN cr.chatUserDTOs cu " +
		   "WHERE cu.staffDTO.staffCode = :staffCode")
	List<ChatRoomDTO> findChatRoomByStaffCode(@Param("staffCode")Integer loggedStaff);

	@Query("SELECT cb " + 
		   "FROM MessengerTestDTO cb " +
		   "WHERE cb.chatRoomNum = :chatRoomNum AND cb.chatBodyDelete = false")
	Page<MessengerTestDTO> chatList(Pageable pageable, Long chatRoomNum);
	
}
