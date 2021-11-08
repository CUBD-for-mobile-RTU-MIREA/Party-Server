package ru.realityfamily.party_server.Models;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Entity
@Table
public class Party {
    @NonNull
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private UUID id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String place;

    @OneToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private Person creator;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar startTime;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar stopTime;

    @Column
    private int maxPeopleCount;

    @OneToMany
    @JoinColumn(name = "party_id")
    private List<Person> peopleList;

    @ElementCollection
    private List<String> images;

    @Column
    private Status status;

    public Party() {
        id = UUID.randomUUID();
        peopleList = new ArrayList<>();
        images = new ArrayList<>();
        status = Status.UNVERIFIED;
    }

    public Party(String name) {
        this();
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getStopTime() {
        return stopTime;
    }

    public void setStopTime(Calendar stopTime) {
        this.stopTime = stopTime;
    }

    public int getMaxPeopleCount() {
        return maxPeopleCount;
    }

    public void setMaxPeopleCount(int maxPeopleCount) {
        this.maxPeopleCount = maxPeopleCount;
    }

    public List<Person> getPeopleList() {
        return peopleList;
    }

    public void setPeopleList(List<Person> peopleList) {
        this.peopleList = peopleList;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        VERIFIED,
        UNVERIFIED
    }
}
