package com.nj.websystem.service;

import com.nj.websystem.model.Patient;
import com.nj.websystem.model.PatientTest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientTestService extends PagingAndSortingRepository<PatientTest, Long> {

}
