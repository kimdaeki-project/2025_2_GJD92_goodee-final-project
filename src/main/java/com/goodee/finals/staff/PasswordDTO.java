package com.goodee.finals.staff;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PasswordDTO {
	private String oldPw;
	private String newPw;
	private String newPwChk;
}
