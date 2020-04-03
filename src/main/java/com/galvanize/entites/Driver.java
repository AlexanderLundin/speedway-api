package com.galvanize.entites;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="birth_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date birthDate;

    @Column(name="nick_name")
    private String nickName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Car car;

    @OneToMany
    @Column(name = "records")
    private List<RaceRecord> records;

    @Column(name="wins")
    private int wins;

    @Column(name="losses")
    private int losses;

    public Driver(){}

    public Driver(String firstName, Date birthDate, Car car) {
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.car = car;
    }

    public Driver(String firstName, String lastName, Date birthDate, String nickName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.nickName = nickName;
    }

    public int getAge() {
        Calendar c = Calendar.getInstance();
        c.setTime(this.birthDate);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DATE);
        LocalDate ll = LocalDate.of(year, month, day);
        LocalDate now = LocalDate.now();
        Period diff = Period.between(ll, now);
        return diff.getYears();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public List<RaceRecord> getRecords() {
        return records;
    }

    public void setRecords(List<RaceRecord> records) {
        this.records = records;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + birthDate +
                ", nickName='" + nickName + '\'' +
                ", car=" + car +
                ", records=" + records +
                ", wins=" + wins +
                ", losses=" + losses +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Driver)) return false;
        Driver driver = (Driver) o;
        return wins == driver.wins &&
                losses == driver.losses &&
                Objects.equals(id, driver.id) &&
                Objects.equals(firstName, driver.firstName) &&
                Objects.equals(lastName, driver.lastName) &&
                Objects.equals(birthDate, driver.birthDate) &&
                Objects.equals(nickName, driver.nickName) &&
                Objects.equals(car, driver.car) &&
                Objects.equals(records, driver.records);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthDate, nickName, car, records, wins, losses);
    }
}
