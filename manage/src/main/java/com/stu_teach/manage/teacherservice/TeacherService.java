package com.stu_teach.manage.teacherservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.stu_teach.manage.entity.Student;
import com.stu_teach.manage.entity.Task;
import com.stu_teach.manage.entity.Teacher;
import com.stu_teach.manage.exception.ManageException;
import com.stu_teach.manage.req.res.AllStudentResponse;
import com.stu_teach.manage.req.res.StudentDeletionResponse;
import com.stu_teach.manage.req.res.TaskCreationRequest;
import com.stu_teach.manage.req.res.TaskCreationResponse;
import com.stu_teach.manage.req.res.TaskDataResponse;
import com.stu_teach.manage.req.res.TaskUpdateRequest;
import com.stu_teach.manage.req.res.TeacherRegisterRequest;
import com.stu_teach.manage.studentrepo.StudentRepo;
import com.stu_teach.manage.taskrepo.taskRepo;
import com.stu_teach.manage.teacherrepo.TeacherRepo;

@Service
public class TeacherService {
	
	@Autowired
	BCryptPasswordEncoder bcryptEncoder;
	
	@Autowired
	private TeacherRepo teacherRepo;
	
	@Autowired
	private StudentRepo studentRepo;
	
	@Autowired
	private taskRepo taskRep;

	@Autowired
	private DepartmentRepo departmentRepo;
	
	public String registerTeacher(TeacherRegisterRequest request) {
		Boolean emailExist = teacherRepo.existsByEmail(request.getEmail());
		Boolean mobileExist = teacherRepo.existsByMobile(request.getMobile());
		if(emailExist) {
			throw new ManageException("Email Already Exists in record!!");
		}
		if(mobileExist) {
			throw new ManageException("Mobile Number Already Exists in record!!");
		}
		
		
		if (!request.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
		    throw new ManageException("Invalid email format!");
		}
		if (!request.getFullName().matches("^[a-zA-Z ]+$")) {
		    throw new ManageException("full name must contain only letters and spaces!");
		}
		if (!request.getMobile().matches("^[0-9]{10}$")) {
		    throw new ManageException("Mobile must contain exactly 10 digits!");
		}

		Teacher teacher = new Teacher();
		teacher.setFullName(request.getFullName());
		teacher.setMobile(request.getMobile());
		teacher.setEmail(request.getEmail());
		teacher.setHashPassword(bcryptEncoder.encode(request.getHashPassword()));
		teacherRepo.save(teacher);
		return "Teacher Registered Successfully!! ";
	}


	public Department createDepartment(DepartmentRequest request) {
		if (ObjectUtils.isEmpty(request.getDepartmentName())) {
			throw new ManageException("Department name cannot be null");
		} 
		Boolean isDepExists = departmentRepo.existsByDepartmentName(request.getDepartmentName());
		if(isDepExists) {
			throw new ManageException("Department Name Already Exists!!");
		}
		Department department = new Department();
		department.setDepartmentName(request.getDepartmentName());
		departmentRepo.save(department);
		return department;
	}
	
	
	public StudentDeletionResponse deleteStudentData(String email) {
		if(ObjectUtils.isEmpty(email)) {
			throw new ManageException("Please provide email!!");
		}
		Boolean emailExist = studentRepo.existsByEmail(email);
		if(!emailExist) {
			throw new ManageException("Email Not Found in records!!");
		}
		Student student = studentRepo.findByEmail(email);
		taskRep.deleteByTaskGivenTo(student.getStudentId());
		if(!ObjectUtils.isEmpty(student.getEmail())) {
			studentRepo.deleteByEmail(student.getEmail());
		}
		StudentDeletionResponse response = new StudentDeletionResponse();
		BeanUtils.copyProperties(student, response);
		return response;
	}
	
	public List<AllStudentResponse> seeAllStudentData() {
		List<Student> list = studentRepo.findAllStudentData();
		if(ObjectUtils.isEmpty(list)) {
			throw new ManageException("No Records Found!! ");
		}
		List<AllStudentResponse> responses = new ArrayList<AllStudentResponse>();
		
		for(Student data : list) {
			AllStudentResponse response = new AllStudentResponse();
			response.setStudentId(data.getStudentId());
			response.setFirstName(data.getFirstName());
			response.setLastName(data.getLastName());
			response.setGender(data.getGender());
			response.setMobile(data.getMobile());
			response.setEmail(data.getEmail());
			response.setBranch(data.getBranch());
			responses.add(response);
		}
		return responses;
	}

	@CachePut(value = "taskData", key = "#request.taskName")
	public TaskCreationResponse createTask(TaskCreationRequest request) {
	    Task task = new Task();

	    if (ObjectUtils.isEmpty(request.getTaskName())) {
	        throw new ManageException("Please Enter Task Name");
	    }
	    Boolean isTaskNameAlreadyExists = taskRep.existsByTaskName(request.getTaskName());
	    if(isTaskNameAlreadyExists) {
	    	 throw new ManageException("Task Name Already Exists Try Another One!! ");
	    }
	    task.setTaskName(request.getTaskName());

	    if (ObjectUtils.isEmpty(request.getTaskGivenBy())) {
	        throw new ManageException("Please Enter Teacher Email Assigning the task");
	    }
	    
	    Integer teacherId = teacherRepo.findTeacherIdByEmail(request.getTaskGivenBy());
	    if (teacherId == null) {
	        throw new ManageException("Teacher Assigning task email not found");
	    }
	    task.setTaskGivenBy(teacherId);

	    if (ObjectUtils.isEmpty(request.getTaskGivenTo())) {
	        throw new ManageException("Please Provide Student Email(s) to assign the task");
	    }

	        Integer studentId = studentRepo.findStudentIdByEmail(request.getTaskGivenTo());
	        if (studentId == null) {
	            throw new ManageException("Student email not found: ");
	        }

		Boolean studentsExists = taskRep.existsByTaskGivenTo(studentId.longValue());
		if (studentsExists) {
			throw new ManageException("Task Is already assign to this student please delete previous!!");
		}

	    task.setTaskGivenTo(studentId);

	    if (ObjectUtils.isEmpty(request.getTaskDeadLine())) {
	        throw new ManageException("Please Provide Task Deadline");
	    }
	    task.setTaskDeadline(request.getTaskDeadLine());
	    task.setTaskStatus("NA");

	    taskRep.save(task);

	    TaskCreationResponse response = new TaskCreationResponse();
	    response.setTaskName(task.getTaskName());
	    response.setTaskGivenBy(request.getTaskGivenBy());
	    response.setTaskGivenTo(studentId);
	    response.setTaskDeadLine(task.getTaskDeadline());
	    response.setTaskStatus(task.getTaskStatus());
	    return response;
	}
	
	//
	@CacheEvict(value = "taskData", key = "#taskName")
	public String deleteTask(String taskName) {
		Boolean isExists = taskRep.existsByTaskName(taskName);
		if(!isExists) {
			throw new ManageException("This Task Name is not Found");
		}else {
			taskRep.deleteByTaskName(taskName);
		}
		
		return taskName + " deleted successfuly!! ";
	}
		
	//
	@CachePut(value = "taskData", key = "#request.taskName")
	public Task updateTaskStatusAndMarks(TaskUpdateRequest request) {
		Boolean isExists = taskRep.existsByTaskName(request.getTaskName());
		if(!isExists) {
			throw new ManageException("This Task Name is not Found");
		}
		Task data = taskRep.findTaskByTaskName(request.getTaskName());
		data.setTaskName(data.getTaskName());
		data.setTaskGivenBy(data.getTaskGivenBy());
		data.setTaskGivenTo(data.getTaskGivenTo());
		data.setTaskDeadline(data.getTaskDeadline());
		data.setTaskStatus(request.getTaskStatus());
		data.setMarks(request.getMarks());
		return taskRep.save(data);
	}
	
	

	public List<TaskDataResponse> getAllTaskData(){
		List<Task> list = taskRep.findAllTaskData();
		if(ObjectUtils.isEmpty(list)) {
			throw new ManageException("No Records Found!! ");
		}
		List<TaskDataResponse> responses = new ArrayList<TaskDataResponse>();
		
		for(Task data : list) {
			TaskDataResponse response = new TaskDataResponse();
			response.setTaskName(data.getTaskName());
			response.setTaskGivenBy(teacherRepo.findTeacherEmailByTeacherId(data.getTaskGivenBy()));
			response.setTaskGivenTo(studentRepo.findStudentEmailByStudentId(data.getTaskGivenTo()));
			response.setTaskStatus(ObjectUtils.isEmpty(data.getTaskStatus())?"NA":data.getTaskStatus());
			response.setMarks(ObjectUtils.isEmpty(data.getMarks())?0:data.getMarks());
			responses.add(response);
		}
		return responses;	
	}
	
}
