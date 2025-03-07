package com.stu_teach.manage.req.res;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskStatusMarksResponse implements Serializable{
	
	private String name;
	private String mobile;
	private String email;
	private String taskStatus;
	private Date taskDeadline;
	private Integer marks;

}
