package com.stu_teach.manage.req.res;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherRegisterRequest {
	
    private String fullName;     
    private String mobile;  
    private String email;
    private String hashPassword;

}
