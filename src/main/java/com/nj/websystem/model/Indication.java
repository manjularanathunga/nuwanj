package com.nj.websystem.model;

import com.nj.websystem.enums.Status;

import java.util.ArrayList;
import java.util.List;

public class Indication {
    private Long id;
    private String indName;
    private Status status;

    public Indication(Long id, String indName, Status status) {
        this.id = id;
        this.indName = indName;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndName() {
        return indName;
    }

    public void setIndName(String indName) {
        this.indName = indName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
