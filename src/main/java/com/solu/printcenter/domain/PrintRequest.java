package com.solu.printcenter.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "t_print_request")

public class PrintRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "color")
    private Boolean color;

    @Column(name = "notes")
    private String notes;

    @Column(name = "requested_by")
    private String requestedBy;

    @Column(name = "request_date")
    private LocalDate requestDate;

    @Column(name = "meeting_owner")
    private String meetingOwner;

    @Column(name = "meeting_date")
    private LocalDate meetingDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Boolean getColor() {
        return color;
    }

    public void setColor(Boolean color) {
        this.color = color;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public String getMeetingOwner() {
        return meetingOwner;
    }

    public void setMeetingOwner(String meetingOwner) {
        this.meetingOwner = meetingOwner;
    }

    public LocalDate getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(LocalDate meetingDate) {
        this.meetingDate = meetingDate;
    }
}
