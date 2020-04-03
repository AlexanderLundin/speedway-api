package com.galvanize.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class CarControllerTest {


    @Autowired
    MockMvc mvc;


    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CarService carService;


    @Autowired
    CarRepository carRepository;

    private List<Car> carListInDatabase = new ArrayList<>();

    @BeforeEach
    void setUp (){
        //create test cars
        ArrayList<String> nickNameList = new ArrayList<>(
                Arrays.asList("BatMobile", "Gwagon", "Caddy", "Green Hornet", "The Black Pearl", "General Lee"));
        ArrayList<Model> modelList = new ArrayList<>(
                Arrays.asList(Model.Ferrari, Model.Alpine, Model.Corvette, Model.Jaguar, Model.Maserati, Model.Porsche));
        String year = "2020";
        long topSpeed = 200L;
        Car car;
        for (int i = 0; i < 6 ; i++) {
            car = new Car(nickNameList.get(i), modelList.get(i), year, Status.AVAILABLE, topSpeed);
            carRepository.save(car);
            carListInDatabase.add(car);
        }
    }


    // Mapper helpers


    public List<Car> mapResultActionsToCarList (ResultActions resultActions) throws UnsupportedEncodingException, JsonProcessingException {
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        List<Car> carList = Arrays.asList(objectMapper.readValue(contentAsString, Car[].class));
        return carList;
    }

    public Car mapResultActionsToCar (ResultActions resultActions) throws UnsupportedEncodingException, JsonProcessingException {
        MvcResult result = resultActions.andReturn();
        String json = result.getResponse().getContentAsString();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        Car car = objectMapper.readValue(json, Car.class);
        return car;
    }


    //CREATE


    @Test
    public void postCar_returnsCar() throws Exception {
        //Setup
        Car expected = carListInDatabase.get(1);
        String url = "/api/cars";
        //Exercise
        ResultActions resultActions = mvc.perform(post(url)
                .content(objectMapper.writeValueAsString(expected))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());
        Car car = mapResultActionsToCar(resultActions);
        //Assert
        assertEquals(expected.getNickName(), car.getNickName());
        //Teardown
    }


    //READ


    @Test
    public void getAllCars_withCarsInDatabase_returnsCarList() throws Exception {
        //Setup
        String url = "/api/cars";
        //Exercise
        ResultActions resultActions = mvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk());
        List<Car> actual = mapResultActionsToCarList(resultActions);
        //Assert
        assertEquals(carListInDatabase.size(), actual.size());
        //Teardown
    }
//
//
//
//    //UPDATE
//
//
//    @Test
//    public void testUpdateCarById() throws Exception {
//        //Setup
//        String url = "/api/cars/" + expected1.getId();
//        //Exercise
//        ResultActions resultActions = mvc.perform(put(url)
//                .content(objectMapper.writeValueAsString(expected2))
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .accept(MediaType.APPLICATION_JSON_VALUE)
//        )
//                .andDo(print())
//                .andExpect(status().isOk());
//        MvcResult result = resultActions.andReturn();
////        String contentAsString = result.getResponse().getContentAsString();
////        Car car = objectMapper.readValue(contentAsString, Car.class);
////        //Assert data was updated
////        assertEquals(expected1.getNickName(), car.getNickName());
//    }

}
