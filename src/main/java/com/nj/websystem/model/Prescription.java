package com.nj.websystem.model;

import javax.persistence.*;

@Entity
@Table(name = "TBL_PRESCRIPTION")
public class Prescription {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "TBL_PRESCRIPTION_SEQ", sequenceName = "TBL_PRESCRIPTION_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_PRESCRIPTION_SEQ")
    private Long id;
    private String dose;
    private String dosevalue;
    private String selectIndication;
    private String selectFinding;
    private String scanNumber;
    @Column(length = 2000)
    private String procedure;
    @Column(length = 2000)
    private String indication;
    @Column(length = 2000)
    private String finding;
    @Column(length = 2000)
    private String impression;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getDosevalue() {
        return dosevalue;
    }

    public void setDosevalue(String dosevalue) {
        this.dosevalue = dosevalue;
    }

    public String getSelectIndication() {
        return selectIndication;
    }

    public void setSelectIndication(String selectIndication) {
        this.selectIndication = selectIndication;
    }

    public String getSelectFinding() {
        return selectFinding;
    }

    public void setSelectFinding(String selectFinding) {
        this.selectFinding = selectFinding;
    }

    public String getScanNumber() {
        return scanNumber;
    }

    public void setScanNumber(String scanNumber) {
        this.scanNumber = scanNumber;
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
}
