package com.goodee.finals.messenger;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MessengerRepository extends JpaRepository<ChatRoomDTO, Long> {

	@Query("SELECT cr " +
		   "FROM ChatRoomDTO cr " +
		   "JOIN cr.chatUserDTOs cu " +
		   "WHERE cu.staffDTO.staffCode = :staffCode")
	List<ChatRoomDTO> findChatRoomByStaffCode(@Param("staffCode")Integer loggedStaff);

	@Query("SELECT cb " + 
		   "FROM MessengerTestDTO cb " +
		   "WHERE cb.chatRoomNum = :chatRoomNum AND cb.chatBodyDelete = false AND cb.chatBodyNum >= :chatGroupLatest")
	Page<MessengerTestDTO> chatList(Pageable pageable, Long chatRoomNum, Long chatGroupLatest);

	List<ChatRoomDTO> findByChatRoomGroupFalseAndChatUserDTOsStaffDTOStaffCode(Integer loggedStaffCode);

	@Query("SELECT cb " +
		   "FROM MessengerTestDTO cb " + 
		   "WHERE cb.chatRoomNum = :chatRoomNum AND cb.staffCode != :staffCode " +
		   "ORDER BY cb.chatBodyDtm DESC")
	List<MessengerTestDTO> getLatest(Long chatRoomNum, Integer staffCode);

	@Modifying
	@Transactional
	@Query("UPDATE ChatUserDTO cu " + 
		   "SET cu.chatBodyNum = :chatBodyNum " + 
		   "WHERE cu.chatRoomDTO.chatRoomNum = :chatRoomNum " + 
		   "AND cu.staffDTO.staffCode = :staffCode")
	int unread(Long chatRoomNum, Integer staffCode, Long chatBodyNum);

	@Query("SELECT cu FROM ChatUserDTO cu WHERE cu.chatRoomDTO.chatRoomNum = :r AND cu.staffDTO.staffCode = :staffCode")
	ChatUserDTO getLatestChat(Long r, Integer staffCode);
	
	@Query("SELECT cb " +
		   "FROM MessengerTestDTO cb " + 
		   "WHERE cb.chatRoomNum = :r AND cb.staffCode != :staffCode AND cb.chatBodyNum > :chatBodyNum")
	List<MessengerTestDTO> unreadCounts(Long r, Integer staffCode, Long chatBodyNum);

	@Query("SELECT cb FROM MessengerTestDTO cb WHERE cb.chatRoomNum = :r ORDER BY cb.chatBodyNum DESC LIMIT 1")
	MessengerTestDTO getTrueLatest(Long r);

	@Query("SELECT cu FROM ChatUserDTO cu WHERE cu.chatRoomDTO.chatRoomNum = :chatRoomNum")
	List<ChatUserDTO> getNotify(Long chatRoomNum);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO chat_user (staff_code, chat_body_num, chat_room_num, chat_group_latest) VALUES (:staffCode, :chatBodyNum, :chatRoomNum, :chatBodyNum)", nativeQuery = true)
	int saveJoinStaffs(Integer staffCode, Long chatRoomNum, Long chatBodyNum);
	
}
