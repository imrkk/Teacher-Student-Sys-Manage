package com.stu_teach.manage.entity;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Task")
public class Task extends BaseVO implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "taskId")
    private Long taskId;

	@Column(name = "taskName")
    private String taskName;
    
	@Column(name = "taskGivenBy")
    private Integer taskGivenBy;
    
    private Integer taskGivenTo;
    
	@Column(name = "taskDeadline")
    private Date taskDeadline;
    
	@Column(name = "taskStatus")
    private String taskStatus;
	
	@Column(name = "marks")
    private Integer marks;


}
