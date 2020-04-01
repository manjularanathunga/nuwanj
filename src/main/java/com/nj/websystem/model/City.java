package com.nj.websystem.model;

import com.nj.websystem.enums.Status;

import javax.persistence.*;

@Entity
@Table(name = "TBL_CITY")
public class City {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "TBL_CITY_SEQ", sequenceName = "TBL_CITY_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_CITY_SEQ")
    private Long id;
    private Long cityName;
    private Status status;
    @ManyToOne
    private District district;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCityName() {
        return cityName;
    }

    public void setCityName(Long cityName) {
        this.cityName = cityName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }
}
