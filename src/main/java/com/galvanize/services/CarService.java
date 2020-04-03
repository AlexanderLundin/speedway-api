package com.galvanize.services;

import com.galvanize.entites.Car;
import com.galvanize.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService{

    private CarRepository carRepository;

    public CarService(CarRepository carRepository){
        this.carRepository = carRepository;
    }


    //CREATE


    public Car save(Car car) {
        return carRepository.save(car);
    }


    //READ


    public List<Car> findAllCars() {
        return carRepository.findAll();
    }

    public Car findCarById(Long id) {
        if(carRepository.findById(id).isPresent()){
            return carRepository.findById(id).get();
        }else{
            throw new RuntimeException("Car id not found");
        }
    }


    //UPDATE


    public Car updateCarById(Long id, Car car) {
        if(carRepository.findById(id).isPresent()){
            carRepository.save(car);
            return carRepository.findById(id).get();
        }else{
            throw new RuntimeException("Driver id not found");
        }
    }


    //DELETE


    public void deleteCarById(Long id) {
        if(carRepository.findById(id).isPresent()){
            carRepository.deleteById(id);
        }else{
            throw new RuntimeException("Car id not found");
        }

    }
}
