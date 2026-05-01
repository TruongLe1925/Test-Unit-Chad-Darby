package com.luv2code.springmvc.repository;

import com.luv2code.springmvc.models.HistoryGrade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryGradeDAO extends CrudRepository<HistoryGrade, Integer> {
    public Iterable<HistoryGrade> findGradeByStudentId(int studentId);
}
