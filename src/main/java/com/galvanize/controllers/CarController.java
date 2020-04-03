package com.galvanize.controllers;

import com.galvanize.entites.Car;
import com.galvanize.responses.Response;
import com.galvanize.services.CarService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {

    CarService carService;

    public CarController(CarService carService){
        this.carService = carService;
    }


    // CREATE


    @PostMapping(value = "/cars", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response postCar(@RequestBody Car car){
        car = carService.save(car);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");
        return new Response(car , headers, HttpStatus.OK);

    }


    //READ

    @GetMapping("/cars")
    public Response getAllCars(){
        List<Car> cars = carService.findAllCars();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");
        return new Response(cars , headers, HttpStatus.OK);
    }

    @GetMapping("/cars/{id}")
    public Response getAllOrderById(@PathVariable long id){
        Car car = carService.findCarById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");
        return new Response(car , headers, HttpStatus.OK);
    }


    //UPDATE


    @PutMapping("/cars/{id}")
    public Response updateCarById(@PathVariable long id, @RequestBody Car car){
        car = carService.updateCarById(id, car);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");
        return new Response(car , headers, HttpStatus.OK);
    }


    //DELETE


    @DeleteMapping("/cars/{id}")
    public void deleteCarById( @PathVariable Long id){
        carService.deleteCarById(id);
    }
}
