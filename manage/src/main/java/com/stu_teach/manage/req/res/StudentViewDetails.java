package com.stu_teach.manage.req.res;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentViewDetails implements Serializable{
	
    private String firstName;  
    private String lastName;   
    private String gender;   
    private String mobile;  
    private String email;
    private String branch;

}
