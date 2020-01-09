package com.nj.websystem.model;

import com.nj.websystem.enums.Status;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TBL_PATIENT_SCAN")
public class PatientScan {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "TBL_PATIENT_SCAN_SEQ", sequenceName = "TBL_PATIENT_SCAN_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_PATIENT_SCAN_SEQ")
    private Long id;
    private String scanNumber;
    private String billingNumber;
    private String patientId;
    private String procedure;
    private String indication;
    private String finding;
    private String impression;
    private String remarks;
    private String actionBy;
    private Date dateCreated;
    private Date lastModified;
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScanNumber() {
        return scanNumber;
    }

    public void setScanNumber(String scanNumber) {
        this.scanNumber = scanNumber;
    }

    public String getBillingNumber() {
        return billingNumber;
    }

    public void setBillingNumber(String billingNumber) {
        this.billingNumber = billingNumber;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public String getFinding() {
        return finding;
    }

    public void setFinding(String finding) {
        this.finding = finding;
    }

    public String getImpression() {
        return impression;
    }

    public void setImpression(String impression) {
        this.impression = impression;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    @Override
    public String toString() {
        return "PatientScan{" +
                "id=" + id +
                ", scanNumber='" + scanNumber + '\'' +
                ", billingNumber='" + billingNumber + '\'' +
                ", patientId='" + patientId + '\'' +
                ", procedure='" + procedure + '\'' +
                ", indication='" + indication + '\'' +
                ", finding='" + finding + '\'' +
                ", impression='" + impression + '\'' +
                ", remarks='" + remarks + '\'' +
                ", actionBy='" + actionBy + '\'' +
                ", dateCreated=" + dateCreated +
                ", lastModified=" + lastModified +
                ", status=" + status +
                '}';
    }
}
