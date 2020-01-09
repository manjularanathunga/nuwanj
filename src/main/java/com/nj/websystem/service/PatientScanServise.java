package com.nj.websystem.service;

import com.nj.websystem.model.PatientScan;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PatientScanServise  extends PagingAndSortingRepository<PatientScan, Long> {

}
