package com.galvanize.controllers;

import com.galvanize.entites.Driver;
import com.galvanize.responses.Response;
import com.galvanize.services.DriverService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DriverController {

    DriverService driverService;

    public DriverController(DriverService driverService){
        this.driverService = driverService;
    }


    // CREATE


    @PostMapping(value = "/drivers", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response postDriver(@RequestBody Driver driver){
        driver = driverService.save(driver);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");
        return new Response(driver , headers, HttpStatus.OK);

    }


    //READ

    @GetMapping("/drivers")
    public Response getAllDrivers(){
        List<Driver> drivers = driverService.findAllDrivers();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");
        return new Response(drivers , headers, HttpStatus.OK);
    }

    @GetMapping("/drivers/{id}")
    public Response getAllOrderById(@PathVariable long id){
        Driver driver = driverService.findDriverById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");
        return new Response(driver , headers, HttpStatus.OK);
    }


    //UPDATE


    @PutMapping("/drivers/{id}")
    public Response updateDriverById(@PathVariable long id, @RequestBody Driver driver){
        driver = driverService.updateDriverById(id, driver);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");
        return new Response(driver , headers, HttpStatus.OK);
    }


    //DELETE


    @DeleteMapping("/drivers/{id}")
    public void deleteDriverById( @PathVariable Long id){
        driverService.deleteDriverById(id);
    }
}
