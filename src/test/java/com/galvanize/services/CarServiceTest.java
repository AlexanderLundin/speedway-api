package com.galvanize.services;

import com.galvanize.entites.Car;
import com.galvanize.entites.Model;
import com.galvanize.entites.Status;
import com.galvanize.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class CarServiceTest {


    @Autowired
    CarService carService;


    @Autowired
    CarRepository carRepository;

    private Car car;


    @BeforeEach
    public void setup(){
        String nickName = "CarDriver";
        String year = "2019";
        Status status = Status.AVAILABLE;
        Long topSpeed = 100L;
        car = new Car( nickName,  Model.Ferrari,  year, status,topSpeed);
    }


    //CREATE


    @Test
    public void saveCarTest() {

        Car actual = carService.save(car);
        assertEquals(car, actual);

    }


    //READ


    @Test
    public void getAllCarsTest(){

        List<Car> expected = new ArrayList<>();
        expected.add(carService.save(car));
        List<Car> actual = carService.findAllCars();

        assertEquals(expected.size(),actual.size());

    }

    @Test
    public void findCarByIdTest(){
        Car actual = carService.save(car);
        Car expected = carService.findCarById(actual.getId());
        assertEquals(expected,actual);
    }


    //UPDATE


    @Test
    public void updateCarNickNameTest(){
        String newNickName = "UpdatedCarDriver";
        carService.save(car);
        car.setNickName("Yellow Wasp");
        car.setTopSpeed(212L);
        Car actual = carService.updateCarById(car.getId(),car);
        assertEquals(car.getNickName(),actual.getNickName());

    }


    //DELETE


    @Test
    public void deleteCarByIdTest(){
        Car actual = carService.save(car);
        carService.deleteCarById(car.getId());
        assertThrows(RuntimeException.class, () -> carService.findCarById(actual.getId()));
    }


}