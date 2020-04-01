package com.nj.websystem.model;

import com.nj.websystem.enums.LabType;
import com.nj.websystem.enums.Status;

import javax.persistence.*;

@Entity
@Table(name = "TBL_PATIENT_TEST")
public class PatientTest {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "TBL_PATIENT_TEST_SEQ", sequenceName = "TBL_PATIENT_TEST_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_PATIENT_TEST_SEQ")
    private Long id;
    @ManyToOne
    private Billing billing;
    @ManyToOne
    private MedicalTest medicalTest;
    @OneToOne
    private PatientTestResult results;
    @OneToOne
    private Prescription prescription;
    private LabType labType;
    private String seenBy;
    private String remarks;
    private Boolean priority;
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Billing getBilling() {
        return billing;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }

    public MedicalTest getMedicalTest() {
        return medicalTest;
    }

    public void setMedicalTest(MedicalTest medicalTest) {
        this.medicalTest = medicalTest;
    }

    public PatientTestResult getResults() {
        return results;
    }

    public void setResults(PatientTestResult results) {
        this.results = results;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public LabType getLabType() {
        return labType;
    }

    public void setLabType(LabType labType) {
        this.labType = labType;
    }

    public String getSeenBy() {
        return seenBy;
    }

    public void setSeenBy(String seenBy) {
        this.seenBy = seenBy;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Boolean getPriority() {
        return priority;
    }

    public void setPriority(Boolean priority) {
        this.priority = priority;
    }
}
