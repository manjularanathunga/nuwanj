package com.nj.websystem.model;

import com.nj.websystem.enums.LabType;
import com.nj.websystem.enums.Status;

import javax.persistence.*;

@Entity
@Table(name = "TBL_PATIENT_TEST_RESULT")
public class PatientTestResult {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "TBL_PATIENT_TEST_RESULT_SEQ", sequenceName = "TBL_PATIENT_TEST_RESULT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_PATIENT_TEST_RESULT_SEQ")
    private Long id;
    @OneToOne
    private PatientTest patientTest;
    private String results;
    private String reference;
    private String actionBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PatientTest getPatientTest() {
        return patientTest;
    }

    public void setPatientTest(PatientTest patientTest) {
        this.patientTest = patientTest;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }
}
