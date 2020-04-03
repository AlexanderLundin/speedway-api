package com.galvanize.entites;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "race")
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="category")
    @Enumerated(EnumType.STRING)
    private RaceCategory raceCategory;

    @Column(name="date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date date;

    @Column(name="best_time")
    private String bestTime;

    @OneToOne()
    @JoinColumn(name = "driver_id", nullable = true)
    private Driver winner;

    @ElementCollection(targetClass = Driver.class)
    @Column(name = "participants")
    private List<Driver> participants;

    public Race(){
        this.participants = new ArrayList<>();
    }

    public Race(String name, RaceCategory raceCategory, Date date, String bestTime, Driver winner) {
        this.name = name;
        this.raceCategory = raceCategory;
        this.date = date;
        this.bestTime = bestTime;
        this.winner = winner;
        this.participants = new ArrayList<>();
        addParticipants(winner);
    }

    @JsonIgnore
    private void addParticipants(Driver winner) {
        this.participants.add(winner);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RaceCategory getRaceCategory() {
        return raceCategory;
    }

    public void setRaceCategory(RaceCategory raceCategory) {
        this.raceCategory = raceCategory;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBestTime() {
        return bestTime;
    }

    public void setBestTime(String bestTime) {
        this.bestTime = bestTime;
    }

    public Driver getWinner() {
        return winner;
    }

    public void setWinner(Driver winner) {
        this.winner = winner;
    }

    public List<Driver> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Driver> participants) {
        this.participants = participants;
    }

    @Override
    public String toString() {
        return "Race{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", raceCategory=" + raceCategory +
                ", date=" + date +
                ", bestTime='" + bestTime + '\'' +
                ", winner=" + winner +
                ", participants=" + participants +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Race)) return false;
        Race race = (Race) o;
        return Objects.equals(id, race.id) &&
                Objects.equals(name, race.name) &&
                raceCategory == race.raceCategory &&
                Objects.equals(date, race.date) &&
                Objects.equals(bestTime, race.bestTime) &&
                Objects.equals(winner, race.winner) &&
                Objects.equals(participants, race.participants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, raceCategory, date, bestTime, winner, participants);
    }
}
