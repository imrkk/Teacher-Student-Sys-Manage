package com.stu_teach.manage.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public abstract class BaseVO {
	
	@LastModifiedDate
	Date modifiedOn;
	@LastModifiedBy
	String modifiedBy;
	@CreatedDate
	@Column(updatable = false)
	Date createdOn;
	@CreatedBy
	@Column(updatable = false)
	String createdBy;

}
