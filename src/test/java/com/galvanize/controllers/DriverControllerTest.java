package com.galvanize.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.entites.Car;
import com.galvanize.entites.Driver;
import com.galvanize.entites.Model;
import com.galvanize.entites.Status;
import com.galvanize.repository.CarRepository;
import com.galvanize.repository.DriverRepository;
import com.galvanize.services.DriverService;
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
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class DriverControllerTest {


    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    DriverService driverService;

    @Autowired
    CarRepository carRepository;

    @Autowired
    DriverRepository driverRepository;

    private List<Driver> driverListInDatabase = new ArrayList<>();

    @BeforeEach
    void setUp (){
        ArrayList<String> firstNameList = new ArrayList<>(
                Arrays.asList("Ricky", "Bobby", "Legend", "Cal", "Naughton", "Jr"));
        //create test drivers
        ArrayList<String> nickNameList = new ArrayList<>(
                Arrays.asList("BatMobile", "Gwagon", "Caddy", "Green Hornet", "The Black Pearl", "General Lee"));
        ArrayList<Model> modelList = new ArrayList<>(
                Arrays.asList(Model.Ferrari, Model.Alpine, Model.Corvette, Model.Jaguar, Model.Maserati, Model.Porsche));
        String year = "2020";
        long topSpeed = 200L;
        Car car;
        Date birthDate = Date.from(Instant.now());
        Driver driver;

        for (int i = 0; i < 6 ; i++) {
            car = new Car(nickNameList.get(i), modelList.get(i), year, Status.AVAILABLE, topSpeed);
            carRepository.save(car);
            driver = new Driver (firstNameList.get(i), birthDate, car);
            driverRepository.save(driver);
            driverListInDatabase.add(driver);
        }
    }


    // Mapper helpers


    public List<Driver> mapResultActionsToDriverList (ResultActions resultActions) throws UnsupportedEncodingException, JsonProcessingException {
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return Arrays.asList(objectMapper.readValue(contentAsString, Driver[].class));
    }

    public Driver mapResultActionsToDriver (ResultActions resultActions) throws UnsupportedEncodingException, JsonProcessingException {
        MvcResult result = resultActions.andReturn();
        String json = result.getResponse().getContentAsString();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return objectMapper.readValue(json, Driver.class);
    }


    //CREATE


    @Test
    public void postDriver_returnsDriver() throws Exception {
        //Setup
        Driver expected = driverListInDatabase.get(1);
        String url = "/api/drivers";
        //Exercise
        ResultActions resultActions = mvc.perform(post(url)
                .content(objectMapper.writeValueAsString(expected))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());
        Driver driver = mapResultActionsToDriver(resultActions);
        //Assert
        assertEquals(expected.getNickName(), driver.getNickName());
        //Teardown
    }


    //READ


    @Test
    public void getAllDrivers_withDriversInDatabase_returnsDriverList() throws Exception {
        //Setup
        String url = "/api/drivers";
        //Exercise
        ResultActions resultActions = mvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk());
        List<Driver> actual = mapResultActionsToDriverList(resultActions);
        //Assert
        assertEquals(driverListInDatabase.size(), actual.size());
        //Teardown
    }



    //UPDATE


    @Test
    public void updateDriverById_withDriverInDatabase_returnsDriver() throws Exception {
        //Setup
        Driver expected = driverListInDatabase.get(1);
        expected.setNickName("New Nickname");
        String url = "/api/drivers/" + expected.getId();
        //Exercise
        ResultActions resultActions = mvc.perform(put(url)
                .content(objectMapper.writeValueAsString(expected))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());
        Driver driver = mapResultActionsToDriver(resultActions);
        //Assert
        assertEquals(driver, expected);
        //Teardown
    }



    //DELETE


    @Test
    public void deleteDriverById_withDriverInDatabase_returnsDriver() throws Exception {
        //Setup
        Driver driver = new Driver();
        driverService.save(driver);
        String url = "/api/drivers/" + driver.getId();
        //Exercise
        mvc.perform(delete(url))
                .andDo(print())
                .andExpect(status().isOk());
        //Assert
        assertThrows(RuntimeException.class, () -> driverService.findDriverById(driver.getId()));
        //Teardown
    }
}
