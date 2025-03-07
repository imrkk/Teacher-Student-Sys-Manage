package com.stu_teach.manage.taskrepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stu_teach.manage.entity.Task;

import jakarta.transaction.Transactional;


@Repository
public interface taskRepo extends JpaRepository<Task, Long>{

	Boolean existsByTaskName(String taskName);

	@Transactional
	@Modifying
	void deleteByTaskName(String taskName);

	@Query(nativeQuery = true, value = "select * from Task t where t.taskName = :taskName")
	Task findTaskByTaskName(String taskName);

	@Query(nativeQuery = true, value = "select * from Task t")
	List<Task> findAllTaskData();

	@Query(nativeQuery = true, value = "select * from Task t where t.taskGivenTo = :studentId")
	Task findByStudentId(Long studentId);

	Boolean existsByTaskGivenTo(Long studentId);

	@Transactional
	@Modifying
	void deleteByTaskGivenTo(Long studentId);

}
