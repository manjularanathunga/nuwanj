package com.nj.websystem.service;

import com.nj.websystem.model.Billing;
import com.nj.websystem.model.PatientMedicalTest;
import com.nj.websystem.model.PatientTest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillingService extends PagingAndSortingRepository<Billing, Long> {

    Optional<Billing> findByBillingNumber(String billingNumber);
}
