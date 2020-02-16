package com.nj.websystem.model;

import com.nj.websystem.enums.Status;

public class Finding {
    private Long id;
    private String findingName;
    private Status status;

    public Finding(Long id, String findingName, Status status) {
        this.id = id;
        this.findingName = findingName;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFindingName() {
        return findingName;
    }

    public void setFindingName(String findingName) {
        this.findingName = findingName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
