package com.stu_teach.manage.teacherrepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stu_teach.manage.entity.Department;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long>{

	Boolean existsByDepartmentName(String departmentName);

}
