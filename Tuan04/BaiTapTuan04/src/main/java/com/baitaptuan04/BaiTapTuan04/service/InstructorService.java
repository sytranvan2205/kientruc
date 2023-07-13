package com.baitaptuan04.BaiTapTuan04.service;

import java.util.List;

import com.baitaptuan04.BaiTapTuan04.entities.Instructor;

public interface InstructorService {
	public List<Instructor> findAll();
	public Instructor findById(Long instructorId);
	public Instructor save(Instructor instructor);
}
