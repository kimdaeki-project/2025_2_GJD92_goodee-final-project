package com.goodee.finals.messenger;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class StompController {
	
	private final SimpMessagingTemplate simpMessagingTemplate;
	
    public StompController(SimpMessagingTemplate template) {
        this.simpMessagingTemplate = template;
    }
    
	@MessageMapping("send")
	public void send(MessengerTestDTO message) throws Exception {
		// Thread.sleep(1000);
		simpMessagingTemplate.convertAndSend("/sub", message);
	}

}
