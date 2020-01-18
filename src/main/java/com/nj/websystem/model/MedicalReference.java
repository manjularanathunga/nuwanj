package com.nj.websystem.model;

import com.nj.websystem.enums.Gender;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "TBL_MEDICAL_REFERENCE")
public class MedicalReference {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "TBL_MEDICAL_REFERENCE_SEQ", sequenceName = "TBL_MEDICAL_REFERENCE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_MEDICAL_REFERENCE_SEQ")
    private Long id;
    @OneToOne
    private MedicalTest medicalTest;
    private Gender gender;
    private BigDecimal ageMin;
    private BigDecimal ageMax;
    private String unit;
    private String reference;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MedicalTest getMedicalTest() {
        return medicalTest;
    }

    public void setMedicalTest(MedicalTest medicalTest) {
        this.medicalTest = medicalTest;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public BigDecimal getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(BigDecimal ageMin) {
        this.ageMin = ageMin;
    }

    public BigDecimal getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(BigDecimal ageMax) {
        this.ageMax = ageMax;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicalTest.getId(), gender, ageMin, ageMax);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MedicalReference reference = (MedicalReference) obj;

        return medicalTest.getId().equals(reference.medicalTest.getId()) &&
                gender.equals(reference.gender) && ageMin.equals(reference.ageMin) && ageMax.equals(reference.ageMax);
    }
}
