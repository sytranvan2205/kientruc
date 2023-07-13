package com.baitaptuan04.BaiTapTuan04.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baitaptuan04.BaiTapTuan04.entities.Course;
import com.baitaptuan04.BaiTapTuan04.repositoty.CourseRepository;
import com.baitaptuan04.BaiTapTuan04.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService{
	
	@Autowired
	private CourseRepository repo;

	@Override
	public List<Course> findAll() {
		return repo.findAll();
	}

	@Override
	public Course saveCourse(Course course) {
		return repo.save(course);
	}

	@Override
	public void deleteCourse(Long id) {
		repo.deleteById(id);
	}
	
	

}
