package com.stu_teach.manage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Teacher")
public class Teacher extends BaseVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "teacherId")
    private Long teacherId;

	@Column(name = "fullName")
    private String fullName;
   
	@Column(name = "mobile")
    private String mobile;
    
	@Column(name = "email")
    private String email;

	@Column(name = "hashPassword")
    private String hashPassword;
	

}
