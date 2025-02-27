package com.stu_teach.manage.req.res;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskCreationResponse implements Serializable{
		
	private String taskName;
	private String taskGivenBy;
	private Integer taskGivenTo;
	private Date taskDeadLine;
	private String taskStatus;
}
