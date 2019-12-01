package com.nj.websystem.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TBL_USER")
public class TblUser {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "TBL_USER_SEQ", sequenceName = "TBL_USER_SEQ" )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_USER_SEQ")
    private Long id;
    private String userId;
    private String fistName;
    private String middleName;
    private String lastName;
    private String passWord;
    private UserRoles userRoles;
    private String userEmail;
    private String userPFNumber;
    private String telNumber;
    private String mobNumber;
    private boolean active;
    private Date dateCreated;
    private String actionBy;
    private Date lastDateModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public UserRoles getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(UserRoles userRoles) {
        this.userRoles = userRoles;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public Date getLastDateModified() {
        return lastDateModified;
    }

    public void setLastDateModified(Date lastDateModified) {
        this.lastDateModified = lastDateModified;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPFNumber() {
        return userPFNumber;
    }

    public void setUserPFNumber(String userPFNumber) {
        this.userPFNumber = userPFNumber;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getMobNumber() {
        return mobNumber;
    }

    public void setMobNumber(String mobNumber) {
        this.mobNumber = mobNumber;
    }
}
