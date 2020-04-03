package com.galvanize.entites;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "model")
    @Enumerated(EnumType.ORDINAL)
    private Model model;

    @Column(name = "year")
    private String year;

    @OneToMany(mappedBy = "car", orphanRemoval = false, cascade = CascadeType.ALL)
    @Column(name = "drivers")
    private List<Driver> drivers;

    @Column(name = "status")
    private Status status;

    @Column(name = "topSpeed")
    private Long topSpeed;


    public Car(){}

    public Car(String nickName, Model model, String year, Status status, Long topSpeed) {
        this.nickName = nickName;
        this.model = model;
        this.year = year;
        this.status = status;
        this.topSpeed = topSpeed;
        this.drivers = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", model=" + model +
                ", yea='" + year + '\'' +
                ", drivers=" + drivers +
                ", status=" + status +
                ", topSpeed=" + topSpeed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id) &&
                Objects.equals(nickName, car.nickName) &&
                model == car.model &&
                Objects.equals(year, car.year) &&
                Objects.equals(drivers, car.drivers) &&
                status == car.status &&
                Objects.equals(topSpeed, car.topSpeed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickName, model, year, drivers, status, topSpeed);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String yea) {
        this.year = yea;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getTopSpeed() {
        return topSpeed;
    }

    public void setTopSpeed(Long topSpeed) {
        this.topSpeed = topSpeed;
    }

    public Double convertToKilometerPerHour() {
        return topSpeed * 1.60934;
    }
}
