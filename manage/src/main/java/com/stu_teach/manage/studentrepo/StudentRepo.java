package com.stu_teach.manage.studentrepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stu_teach.manage.entity.Student;
import com.stu_teach.manage.req.res.AllStudentResponse;

import jakarta.transaction.Transactional;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long>{

	@Query(nativeQuery = true, value = "select * from Student s where s.studentId = :id")
	Student findAllByStudentId(Long id);

	Student findByEmail(String email);

	Boolean existsByEmail(String email);
	
	Boolean existsByMobile(String mobile);

	@Transactional
	@Modifying
	void deleteByEmail(String email);

	@Query(nativeQuery = true, value = "select * from Student s")
	List<Student> findAllStudentData();

	@Query(nativeQuery = true, value = "select s.studentId from Student s where s.email = :email")
	Integer findStudentIdByEmail(String email);

	@Query(nativeQuery = true, value = "select s.email from Student s where s.studentId = :taskGivenTo")
	String findStudentEmailByStudentId(Integer taskGivenTo);

	@Query(nativeQuery = true, value = "select * from Student s where s.email = :email")
	Student findByStudentEmail(String email);

}
