package com.baitaptuan04.BaiTapTuan04.service;

import java.util.List;

import com.baitaptuan04.BaiTapTuan04.entities.Course;

public interface CourseService {
	public List<Course> findAll();
	public Course saveCourse(Course course);
	public void deleteCourse(Long id);
}
