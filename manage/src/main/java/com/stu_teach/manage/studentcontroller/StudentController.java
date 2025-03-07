package com.stu_teach.manage.studentcontroller;


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
import com.stu_teach.manage.exception.ManageException;
import com.stu_teach.manage.req.res.StudentRegisterRequest;
import com.stu_teach.manage.req.res.StudentSelfRecordUpdateReqRes;
import com.stu_teach.manage.req.res.StudentViewDetails;
import com.stu_teach.manage.req.res.TaskStatusMarksRespo;
import com.stu_teach.manage.req.res.TaskStatusMarksResponse;
import com.stu_teach.manage.security.JWTHelper;
import com.stu_teach.manage.security.JwtRequest;
import com.stu_teach.manage.security.JwtResponse;
import com.stu_teach.manage.studentservice.StudentService;

@RestController
@RequestMapping("student")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;


    @Autowired
    private JWTHelper jwtHelper;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerStudent(@RequestBody StudentRegisterRequest request) {
		return new ResponseEntity<>(studentService.registerStudent(request), HttpStatus.OK);
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
    
	@PostMapping("/viewDetails")
	public ResponseEntity<StudentViewDetails> viewStudentDetail(@RequestParam(required = true) String email) {
		return new ResponseEntity<>(studentService.getStudentData(email), HttpStatus.OK);
	}
	
	@PostMapping("/updateDetails")
	public ResponseEntity<StudentSelfRecordUpdateReqRes> studentSelfRecordUpdate(@RequestBody StudentSelfRecordUpdateReqRes request) {
		return new ResponseEntity<>(studentService.studentSelfRecordUpdate(request), HttpStatus.OK);
	}
	
	@GetMapping("/checkTaskStatusMarks")
	public ResponseEntity<TaskStatusMarksResponse> taskStatusAndMarks(@RequestParam String email) {
		return new ResponseEntity<>(studentService.getMarksAndTaskStatus(email), HttpStatus.OK);
	}

	

}
