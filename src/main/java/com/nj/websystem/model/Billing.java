package com.nj.websystem.model;

import com.nj.websystem.enums.Status;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "TBL_BILLING")
public class Billing {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "TBL_BILLING_SEQ", sequenceName = "TBL_BILLING_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_BILLING_SEQ")
    private Long id;
    private String billingNumber;
    @ManyToOne
    private Patient patient;
    private Boolean priority;
    private String actionBy;
    private Date dateCreated;
    private Date lastModified;
    private Status status;
    @OneToMany(mappedBy = "billing", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PatientTest> listOfPatientTst;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillingNumber() {
        return billingNumber;
    }

    public void setBillingNumber(String billingNumber) {
        this.billingNumber = billingNumber;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Boolean getPriority() {
        return priority;
    }

    public void setPriority(Boolean priority) {
        this.priority = priority;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<PatientTest> getListOfPatientTst() {
        return listOfPatientTst;
    }

    public void setListOfPatientTst(List<PatientTest> listOfPatientTst) {
        this.listOfPatientTst = listOfPatientTst;
    }
}
