package com.galvanize.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.entites.Car;
import com.galvanize.entites.Model;
import com.galvanize.entites.Status;
import com.galvanize.repository.CarRepository;
import com.galvanize.services.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CarControllerTest {


    @Autowired
    MockMvc mvc;


    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CarService carService;


    @Autowired
    CarRepository carRepository;

    Car expected1;
    Car expected2;

    @BeforeEach
    void setUp (){
        //create test cars
        String nickName = "BatMobile";
        Model model = Model.Ferrari;
        String year = "2020";
        Status status = Status.AVAILABLE;
        long topSpeed = 200L;
        expected1 = new Car(nickName, model, year, status, topSpeed);
        nickName = "G Wagon";
        model = Model.Maserati;
        year = "2020";
        status = Status.AVAILABLE;
        topSpeed = 200L;
        expected2 = new Car(nickName, model, year, status, topSpeed);
        carRepository.save(expected1);
    }


    //CREATE


    @Test
    public void testPostCar() throws Exception {
        //Setup
        String url = "/api/cars";
        //Exercise
        ResultActions resultActions = mvc.perform(post(url)
                .content(objectMapper.writeValueAsString(expected2))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
//        ResponseEntity response = objectMapper.readValue(contentAsString, ResponseEntity.class);
//        Car car = (Car) response.getBody();
//        //Assert
//        assertEquals(expected2.getNickName(), car.getNickName());
//        //Teardown
    }


    //READ


    @Test
    public void testGetAllOrders() throws Exception {
        //Setup
        String url = "/api/cars";
        //Exercise
        ResultActions resultActions = mvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk());
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
//        List<Car> cars = Arrays.asList(objectMapper.readValue(contentAsString, Car[].class));
//        int actual = cars.size();
//        int unexpected = 0;
//        //Assert
//        assertNotEquals(unexpected, actual);
        //Teardown
    }



    //UPDATE


    @Test
    public void testUpdateCarById() throws Exception {
        //Setup
        String url = "/api/cars/" + expected1.getId();
        //Exercise
        ResultActions resultActions = mvc.perform(put(url)
                .content(objectMapper.writeValueAsString(expected2))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());
        MvcResult result = resultActions.andReturn();
//        String contentAsString = result.getResponse().getContentAsString();
//        Car car = objectMapper.readValue(contentAsString, Car.class);
//        //Assert data was updated
//        assertEquals(expected1.getNickName(), car.getNickName());
    }

}
