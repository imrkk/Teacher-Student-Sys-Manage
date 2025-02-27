package com.stu_teach.manage.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.stu_teach.manage.entity.Student;
import com.stu_teach.manage.entity.Teacher;
import com.stu_teach.manage.studentrepo.StudentRepo;
import com.stu_teach.manage.teacherrepo.TeacherRepo;



public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	private StudentRepo studentRepo;
	
	@Autowired
	private TeacherRepo teacherRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {		
		Student student = studentRepo.findByEmail(email);
        if (student != null) {
            return new User(student.getEmail(), student.getPasswordHash(), List.of(new SimpleGrantedAuthority("ROLE_STUDENT")));
        }

        Teacher teacher = teacherRepo.findByEmail(email);
        if (teacher != null) {
            return new User(teacher.getEmail(), teacher.getHashPassword(), List.of(new SimpleGrantedAuthority("ROLE_STUDENT")));
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
	}
	
	
}
