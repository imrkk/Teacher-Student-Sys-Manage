package com.stu_teach.manage.teacherrepo;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stu_teach.manage.entity.Teacher;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, Long>{

	Boolean existsByEmail(String email);

	Boolean existsByMobile(String mobile);

	Teacher findByEmail(String email);

	@Query(nativeQuery = true, value = "select t.teacherId from Teacher t where t.email = :email")
	Integer findTeacherIdByEmail(String email);

	@Query(nativeQuery = true, value = "select t.teacherId from Teacher t where t.teacherId = :taskGivenBy")
	String findTeacherEmailByTeacherId(Integer taskGivenBy);

}
