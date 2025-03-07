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
@Table(name = "Student")
public class Student extends BaseVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "studentId")
    private Long studentId;

	@Column(name = "firstName")
    private String firstName;
    
	@Column(name = "lastName")
    private String lastName;
    
	@Column(name = "gender")
    private String gender;
    
	@Column(name = "mobile")
    private String mobile;
    
	@Column(name = "email")
    private String email;
	
	@Column(name = "branch")
    private String branch;

	@Column(name = "passwordHash")
    private String passwordHash;
	

}