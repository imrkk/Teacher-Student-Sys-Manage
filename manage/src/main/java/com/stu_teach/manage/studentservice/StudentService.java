package com.stu_teach.manage.studentservice;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.stu_teach.manage.entity.Student;
import com.stu_teach.manage.entity.Task;
import com.stu_teach.manage.exception.ManageException;
import com.stu_teach.manage.req.res.StudentRegisterRequest;
import com.stu_teach.manage.req.res.StudentSelfRecordUpdateReqRes;
import com.stu_teach.manage.req.res.StudentViewDetails;
import com.stu_teach.manage.req.res.TaskStatusMarksResponse;
import com.stu_teach.manage.studentrepo.StudentRepo;
import com.stu_teach.manage.taskrepo.taskRepo;


@Service
public class StudentService {
	
	@Autowired
	BCryptPasswordEncoder bcryptEncoder;
	
	@Autowired
	private StudentRepo studentRepo;
	
	@Autowired
	private taskRepo taskRep;
	

	public String registerStudent(StudentRegisterRequest request) {
		Boolean emailExist = studentRepo.existsByEmail(request.getEmail());
		Boolean mobileExist = studentRepo.existsByMobile(request.getMobile());
		if(emailExist) {
			throw new ManageException("Email Already Exists in record!!");
		}
		if(mobileExist) {
			throw new ManageException("Mobile Number Already Exists in record!!");
		}
		
		
		if (!request.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
		    throw new ManageException("Invalid email format!");
		}
		if (!request.getFirstName().matches("^[a-zA-Z]+$")) {
		    throw new ManageException("First name must contain only letters!");
		}
		if (!request.getLastName().matches("^[a-zA-Z]+$")) {
		    throw new ManageException("Last name must contain only letters!");
		}
		if (!request.getGender().matches("^(Male|Female|Other)$")) {
		    throw new ManageException("Gender must be 'Male', 'Female', or 'Other'!");
		}
		if (!request.getMobile().matches("^[0-9]{10}$")) {
		    throw new ManageException("Mobile must contain exactly 10 digits!");
		}

		Student student = new Student();
		student.setFirstName(request.getFirstName());
		student.setLastName(request.getLastName());
		student.setGender(request.getGender());
		student.setMobile(request.getMobile());
		student.setEmail(request.getEmail());
		student.setBranch(request.getBranch());
		student.setPasswordHash(bcryptEncoder.encode(request.getPasswordHash()));
		studentRepo.save(student);
		return "Student Registered Successfully!! ";
	}
	
	//@Cacheable(value="studentData",key="#email")
	public StudentViewDetails getStudentData(String email) {
		if(ObjectUtils.isEmpty(email)) {
			throw new ManageException("Please Enter Email");
		}
		Student student = studentRepo.findByEmail(email);
		if(ObjectUtils.isEmpty(student)) {
			throw new ManageException("Record with this email does not exists!!");
		}
		StudentViewDetails details = new StudentViewDetails();
		BeanUtils.copyProperties(student, details);
		return details;
	}
	
	
	public StudentSelfRecordUpdateReqRes studentSelfRecordUpdate(StudentSelfRecordUpdateReqRes request) {
		if(ObjectUtils.isEmpty(request.getEmail())) {
			throw new ManageException("Please Enter Email");
		}
		Student student = studentRepo.findByEmail(request.getEmail());
		Boolean emailExist = studentRepo.existsByEmail(request.getEmail());
		if(!emailExist) {
			throw new ManageException("Email does not exists in record!!");
		}
		
		if(ObjectUtils.isEmpty(request.getFirstName()) || ObjectUtils.isEmpty(request.getLastName()) || 
				ObjectUtils.isEmpty(request.getGender()) || ObjectUtils.isEmpty(request.getMobile()) 
				||ObjectUtils.isEmpty(request.getEmail())) {
			throw new ManageException("First Name , LastName, Gender, Mobile, Email Cannot be Blank!! ");
		}
		
		if (!request.getFirstName().matches("^[a-zA-Z]+$")) {
		    throw new ManageException("First name must contain only letters!");
		}
		if (!request.getLastName().matches("^[a-zA-Z]+$")) {
		    throw new ManageException("Last name must contain only letters!");
		}
		if (!request.getGender().matches("^(Male|Female|Other)$")) {
		    throw new ManageException("Gender must be 'Male', 'Female', or 'Other'!");
		}
		if (!request.getMobile().matches("^[0-9]{10}$")) {
		    throw new ManageException("Mobile must contain exactly 10 digits!");
		}
		
		BeanUtils.copyProperties(request, student);
		studentRepo.save(student);
		
	    StudentSelfRecordUpdateReqRes response = new StudentSelfRecordUpdateReqRes();
	    BeanUtils.copyProperties(student, response);
		
		return response;
	}
	
	
        //@Cacheable(value="studentData",key="#email")
	public TaskStatusMarksResponse getMarksAndTaskStatus(String email) {
		if(ObjectUtils.isEmpty(email)) {
			throw new ManageException("Please Provide Email");
		}
		Boolean emailExist = studentRepo.existsByEmail(email);
		if(!emailExist) {
			throw new ManageException("Email does not exists in record!!");
		}
		Student student = studentRepo.findByStudentEmail(email);
		Boolean dataExistsInTask = taskRep.existsByTaskGivenTo(student.getStudentId());
		if(!dataExistsInTask) {
			throw new ManageException("Sorry You are not assign to any task!! ");
		}
		Task task = taskRep.findByStudentId(student.getStudentId());
		TaskStatusMarksResponse marksResponse = new TaskStatusMarksResponse();
		marksResponse.setName(student.getFirstName() + " " + student.getLastName());
		marksResponse.setMobile(student.getMobile());
		marksResponse.setEmail(student.getEmail());
		marksResponse.setTaskStatus(ObjectUtils.isEmpty(task.getTaskStatus())?"NA":task.getTaskStatus());
		marksResponse.setTaskDeadline(task.getTaskDeadline());
		marksResponse.setMarks(ObjectUtils.isEmpty(task.getMarks())?0:task.getMarks());
		return marksResponse;
	}
	
	

}
