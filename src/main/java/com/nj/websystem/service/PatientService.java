package com.nj.websystem.service;

import com.nj.websystem.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PatientService extends PagingAndSortingRepository<Patient, Long> {

    List<Patient> findByPatientId(String patientId);

    @Query("Select c.patientId from Patient c where c.patientId like :patientId%")
    List<String> findByPatientIdContaining(String patientId);

    @Query("Select c.patientId from Patient c where c.patientId like :patientId%")
    Page<String> findByPatientIdContaining(String patientId, Pageable pageable);

    // @Query(value = FIND_PROJECTS, nativeQuery = true)
    // List<Patient> findByPatientIdContaining(String patientId, Pageable pageable);

    List<Patient> findByNicNumber(String nicNumber, Pageable pageable);

    // @Query("From MedicalTest ORDER BY patient_name ASC")
    Page<Patient> findAll(Pageable pageable);

    Patient findAllById(Long id);

    public List<Patient> getAllByDateCreatedBetween(Date startDate, Date endDate);

    //long countByDateCreatedBetween(Date startDate, Date endDate);

    //long countByAll();
}
