package com.baitaptuan04.BaiTapTuan04.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baitaptuan04.BaiTapTuan04.entities.Course;
import com.baitaptuan04.BaiTapTuan04.entities.Instructor;
import com.baitaptuan04.BaiTapTuan04.service.CourseService;
import com.baitaptuan04.BaiTapTuan04.service.InstructorService;

@RestController
@RequestMapping("/api")
public class HomeController {
	@Autowired
	private InstructorService instructorService;
	@Autowired
	private CourseService courseService;
	
	@GetMapping("/instructors")
	public @ResponseBody List<Instructor> getAllInstructor(){
		List<Instructor> instructors=  instructorService.findAll();
		return instructors;
	}
	
	@GetMapping("/courses")
	public List<Course> getAllCourse(){
		return courseService.findAll();
	}
	
	@PostMapping("/courses")
	public Course saveCourse(@RequestBody Course course) {
		Instructor instructor = instructorService.findById(course.getInstructor().getId());
		if(instructor == null) {
			instructorService.save(course.getInstructor());
		}
		course.setInstructor(instructor);
		return courseService.saveCourse(course);
	}

}
