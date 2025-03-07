package com.stu_teach.manage.req.res;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskCreationRequest {
	
	private String taskName;
	private String taskGivenBy;
	private String taskGivenTo;
	private Date taskDeadLine;
	private String taskStatus;
	

}
