package com.stu_teach.manage.req.res;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRegisterRequest {
	
    private String firstName;  
    private String lastName;   
    private String gender;   
    private String mobile;  
    private String email;
    private String branch;
    private String passwordHash;

}
