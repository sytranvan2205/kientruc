package com.baitaptuan04.BaiTapTuan04.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baitaptuan04.BaiTapTuan04.entities.Instructor;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long>{


}
