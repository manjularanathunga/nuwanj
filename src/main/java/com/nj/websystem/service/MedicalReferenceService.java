package com.nj.websystem.service;

import com.nj.websystem.enums.TestType;
import com.nj.websystem.model.MedicalReference;
import com.nj.websystem.model.MedicalTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface MedicalReferenceService extends JpaRepository<MedicalReference, Long> {

    List<MedicalReference> findAllByMedicalTestAndAgeMaxGreaterThanEqualAndAgeMaxLessThanEqual(MedicalTest medicalTest, BigDecimal ageMin,BigDecimal ageMax);

}
