package com.stu_teach.manage.security;



import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JwtResponse {
	
	private String jwtToken;
	private String userName;

}
