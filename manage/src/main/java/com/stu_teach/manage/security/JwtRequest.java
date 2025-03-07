package com.stu_teach.manage.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequest {
	
	private String email;
	private String password;

}
