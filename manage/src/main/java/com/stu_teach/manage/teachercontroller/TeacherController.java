package com.stu_teach.manage.teachercontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stu_teach.manage.entity.Task;
import com.stu_teach.manage.exception.ManageException;
import com.stu_teach.manage.req.res.AllStudentResponse;
import com.stu_teach.manage.req.res.StudentDeletionResponse;
import com.stu_teach.manage.req.res.TaskCreationRequest;
import com.stu_teach.manage.req.res.TaskCreationResponse;
import com.stu_teach.manage.req.res.TaskDataResponse;
import com.stu_teach.manage.req.res.TaskUpdateRequest;
import com.stu_teach.manage.req.res.TeacherRegisterRequest;
import com.stu_teach.manage.security.JWTHelper;
import com.stu_teach.manage.security.JwtRequest;
import com.stu_teach.manage.security.JwtResponse;
import com.stu_teach.manage.teacherservice.TeacherService;

@RestController
@RequestMapping("teacher")
public class TeacherController {
	
	@Autowired
	private TeacherService teacherService;
	
	
	@Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;


    @Autowired
    private JWTHelper jwtHelper;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerStudent(@RequestBody TeacherRegisterRequest request) {
		return new ResponseEntity<>(teacherService.registerTeacher(request), HttpStatus.OK);
	}
	
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

        this.doAuthenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.jwtHelper.generateToken(userDetails);
        JwtResponse response = JwtResponse.builder()
                .jwtToken("bearer "+token)
                .userName(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

      
    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
        	throw new ManageException(" Invalid Username or Password  !!");
        }

    }

    
    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
    
	@PostMapping("/deleteStudentByTeacher")
	public ResponseEntity<StudentDeletionResponse> registerStudent(@RequestParam(required = true) String email) {
		return new ResponseEntity<>(teacherService.deleteStudentData(email), HttpStatus.OK);
	}
	
	@PostMapping("/seeAllStudentData")
	public ResponseEntity<List<AllStudentResponse>> allStudentData() {
		return new ResponseEntity<>(teacherService.seeAllStudentData(), HttpStatus.OK);
	}
	
	@PostMapping("/createTask")
	public ResponseEntity<TaskCreationResponse> createTask(@RequestBody TaskCreationRequest request) {
		return new ResponseEntity<>(teacherService.createTask(request), HttpStatus.OK);
	}
	
	@PostMapping("/deleteTask")
	public ResponseEntity<String> deleteTask(@RequestParam(required = true) String taskName) {
		return new ResponseEntity<>(teacherService.deleteTask(taskName), HttpStatus.OK);
	}
	
	@PostMapping("/taskStatusMarksUpdate")
	public ResponseEntity<Task> taskStatusMarksUpdate(@RequestBody TaskUpdateRequest request) {
		return new ResponseEntity<>(teacherService.updateTaskStatusAndMarks(request), HttpStatus.OK);
	}
	
	@GetMapping("/getAllTaskData")
	public ResponseEntity<List<TaskDataResponse>> allTaskData() {
		return new ResponseEntity<>(teacherService.getAllTaskData(), HttpStatus.OK);
	}
	

}
