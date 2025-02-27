package com.stu_teach.manage.req.res;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskUpdateRequest {
	
	private String taskName;
	private String taskStatus;
	private Integer marks;

}
