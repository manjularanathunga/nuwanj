package com.nj.websystem.model;

import javax.persistence.*;

@Entity
@Table(name = "TBL_USER_SCREEN")
public class UserScreen {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "TBL_USER_SCREEN_SEQ", sequenceName = "TBL_USER_SCREEN_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_USER_SCREEN_SEQ")
    private Long id;
    @ManyToOne
    private SystemScreen systemScreen;
    @ManyToOne
    private UserScreen screen;
    private String prop;
    private boolean readEnabled;
    private boolean writeEnabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    public boolean isReadEnabled() {
        return readEnabled;
    }

    public void setReadEnabled(boolean readEnabled) {
        this.readEnabled = readEnabled;
    }

    public boolean isWriteEnabled() {
        return writeEnabled;
    }

    public void setWriteEnabled(boolean writeEnabled) {
        this.writeEnabled = writeEnabled;
    }
}
