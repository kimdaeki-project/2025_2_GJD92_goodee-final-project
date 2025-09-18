package com.goodee.finals.messenger;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessengerRepository extends JpaRepository<ChatRoomDTO, Long> {
	
}
