package com.goodee.finals.messenger;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class MessengerTestDTO {
	private MessengerType type;
	private String contents;
	private String sender;
}
