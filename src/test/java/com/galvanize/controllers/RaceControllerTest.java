package com.galvanize.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.entites.*;
import com.galvanize.repository.CarRepository;
import com.galvanize.repository.DriverRepository;
import com.galvanize.services.DriverService;
import com.galvanize.services.RaceService;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class RaceControllerTest {


    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RaceService raceService;

    @Autowired
    DriverService driverService;

    @Autowired
    CarRepository carRepository;

    @Autowired
    DriverRepository driverRepository;

    private List<Race> raceListInDatabase = new ArrayList<>();

    @BeforeEach
    void setUp (){
        // create races
        ArrayList<String> raceNameList = new ArrayList<>(
                Arrays.asList("Grand Prix", "Indi 500", "Speedway", "Zoom", "Boom Town", "Turtle And Hare"));
        ArrayList<String> firstNameList = new ArrayList<>(
                Arrays.asList("Ricky", "Bobby", "Legend", "Cal", "Naughton", "Jr"));
        //drivers
        ArrayList<String> nickNameList = new ArrayList<>(
                Arrays.asList("BatMobile", "Gwagon", "Caddy", "Green Hornet", "The Black Pearl", "General Lee"));
        //cars
        ArrayList<Model> modelList = new ArrayList<>(
                Arrays.asList(Model.Ferrari, Model.Alpine, Model.Corvette, Model.Jaguar, Model.Maserati, Model.Porsche));
        String year = "2020";
        long topSpeed = 200L;
        Car car;
        Date birthDate = Date.from(Instant.now());
        Driver driver;
        Race race;
        RaceCategory raceCategory = RaceCategory.DRAG;
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        String bestTime = "11:11:11";
        for (int i = 0; i < 6 ; i++) {
            car = new Car(nickNameList.get(i), modelList.get(i), year, Status.AVAILABLE, topSpeed);
            carRepository.save(car);
            driver = new Driver (firstNameList.get(i), birthDate, car);
            driverRepository.save(driver);
            race = new Race(raceNameList.get(i), raceCategory, date, bestTime, driver);
            raceService.save(race);
            raceListInDatabase.add(race);
        }
    }



    // Mapper helpers


    public List<Race> mapResultActionsToRaceList (ResultActions resultActions) throws UnsupportedEncodingException, JsonProcessingException {
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return Arrays.asList(objectMapper.readValue(contentAsString, Race[].class));
    }

    public Race mapResultActionsToRace (ResultActions resultActions) throws UnsupportedEncodingException, JsonProcessingException {
        MvcResult result = resultActions.andReturn();
        String json = result.getResponse().getContentAsString();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return objectMapper.readValue(json, Race.class);
    }


    //CREATE


    @Test
    public void postRace_returnsRace() throws Exception {
        //Setup
        Race expected = raceListInDatabase.get(1);
        String url = "/api/races";
        //Exercise
        ResultActions resultActions = mvc.perform(post(url)
                .content(objectMapper.writeValueAsString(expected))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());
        Race race = mapResultActionsToRace(resultActions);
        //Assert
        assertEquals(expected.getName(), race.getName());
        //Teardown
    }


    //READ


    @Test
    public void getAllRaces_withRacesInDatabase_returnsRaceList() throws Exception {
        //Setup
        String url = "/api/races";
        //Exercise
        ResultActions resultActions = mvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk());
        List<Race> actual = mapResultActionsToRaceList(resultActions);
        //Assert
        assertEquals(raceListInDatabase.size(), actual.size());
        //Teardown
    }



    //UPDATE


    @Test
    public void updateRaceById_withRaceInDatabase_returnsRace() throws Exception {
        //Setup
        Race expected = raceListInDatabase.get(1);
        expected.setName("New Race");
        String url = "/api/races/" + expected.getId();
        //Exercise
        ResultActions resultActions = mvc.perform(put(url)
                .content(objectMapper.writeValueAsString(expected))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());
        Race race = mapResultActionsToRace(resultActions);
        //Assert
        assertEquals(expected.getName(), race.getName());
        //Teardown
    }



    //DELETE


    @Test
    public void deleteRaceById_withRaceInDatabase_returnsRace() throws Exception {
        //Setup
        Race race = new Race();
        raceService.save(race);
        String url = "/api/races/" + race.getId();
        //Exercise
        mvc.perform(delete(url))
                .andDo(print())
                .andExpect(status().isOk());
        //Assert
        assertThrows(RuntimeException.class, () -> raceService.findRaceById(race.getId()));
        //Teardown
    }
}
