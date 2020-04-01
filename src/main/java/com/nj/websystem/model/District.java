package com.nj.websystem.model;

import com.nj.websystem.enums.Status;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TBL_DISTRICT")
public class District {
    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "TBL_DISTRICT_SEQ", sequenceName = "TBL_DISTRICT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_DISTRICT_SEQ")
    private Long id;
    private Long districtName;
    private Status status;
    @OneToMany(mappedBy = "district", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<City> lstOfCity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDistrictName() {
        return districtName;
    }

    public void setDistrictName(Long districtName) {
        this.districtName = districtName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<City> getLstOfCity() {
        return lstOfCity;
    }

    public void setLstOfCity(List<City> lstOfCity) {
        this.lstOfCity = lstOfCity;
    }
}
