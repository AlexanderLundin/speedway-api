package com.galvanize.services;

import com.galvanize.entites.Driver;
import com.galvanize.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {


    @Autowired
    DriverRepository driverRepository;


    //CREATE


    public Driver save(Driver driver) {
        if(driver != null){
            return driverRepository.save(driver);
        }else{
            throw new RuntimeException("driver should not be null");
        }
    }


    //READ


    public List<Driver> findAllDrivers() {
        return driverRepository.findAll();
    }

    public Driver findDriverById(Long id) {
        if(driverRepository.findById(id).isPresent()){
            return driverRepository.findById(id).get();
        }else{
            throw new RuntimeException("Driver id not found");
        }
    }


    //UPDATE


    public Driver updateDriverById(Long id, Driver driver) {
        if(driverRepository.findById(id).isPresent()){
            driverRepository.save(driver);
            return driverRepository.findById(id).get();
        }else{
            throw new RuntimeException("Driver id not found");
        }
    }


    //DELETE


    public void deleteDriverById(Long id) {
        if(driverRepository.findById(id).isPresent()){
            driverRepository.deleteById(id);
        }else{
            throw new RuntimeException("Driver id not found");
        }

    }
}
