package com.goodee.finals.messenger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class StompController {
	
	@Autowired
	private MessengerService messengerService;
	
	private final SimpMessagingTemplate simpMessagingTemplate;
	
    public StompController(SimpMessagingTemplate template) {
        this.simpMessagingTemplate = template;
    }
    
	@MessageMapping("/chat/{chatRoomNum}")
	public void send(@DestinationVariable("chatRoomNum") Long chatRoomNum, MessengerTestDTO message) throws Exception {
		message.setChatRoomNum(chatRoomNum);
		messengerService.saveChat(message);
		simpMessagingTemplate.convertAndSend("/sub/chat/" + chatRoomNum, message);
	}
	
	@MessageMapping("/notify/{staffCode}")
	public void notify(@DestinationVariable("staffCode") Integer staffCode, NotificationDTO message) throws Exception {
		simpMessagingTemplate.convertAndSend("/sub/notify/" + staffCode, message);
	}

}
