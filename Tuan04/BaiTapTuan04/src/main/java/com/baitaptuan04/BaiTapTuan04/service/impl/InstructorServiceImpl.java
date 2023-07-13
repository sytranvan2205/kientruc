package com.baitaptuan04.BaiTapTuan04.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baitaptuan04.BaiTapTuan04.entities.Instructor;
import com.baitaptuan04.BaiTapTuan04.repositoty.InstructorRepository;
import com.baitaptuan04.BaiTapTuan04.service.InstructorService;

@Service
public class InstructorServiceImpl implements InstructorService{

	@Autowired
	private InstructorRepository repo;
	
	
	@Override
	public List<Instructor> findAll() {
		List<Instructor> instructors =  repo.findAll();
		return instructors;
	}


	@Override
	public Instructor findById(Long instructorId) {
		return repo.findById(instructorId).get();
	}


	@Override
	public Instructor save(Instructor instructor) {
		return repo.save(instructor);
	}

}
