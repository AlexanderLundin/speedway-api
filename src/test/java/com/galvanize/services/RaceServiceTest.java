package com.galvanize.services;

import com.galvanize.entites.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RaceServiceTest {


    @Autowired
    RaceService raceService;

    @Autowired
    DriverService driverService;

    @Autowired
    CarService carService;

    private Race race;
    private Car car;
    private List<Race> raceList = new ArrayList<>();
    @BeforeEach
    public void setup() {
        //Setup
        String firstName = "name";
        String lastName = "name2";
        String nickName = "nickName";
        Driver driver = new Driver(firstName, lastName, Date.valueOf(LocalDate.of(1990,11,16)), nickName);
        driverService.save(driver);
        car = new Car("Green Hornet", Model.Alpine, "1960", Status.AVAILABLE, 198L);
        carService.save(car);
        String name = "name";
        RaceCategory raceCategory = RaceCategory.DRAG;
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        String bestTime = "11:11:11";
        Driver winner = driver;
        race = new Race(name, raceCategory, date, bestTime, winner);
        raceService.save(race);
        raceList.add(race);
    }


    //CREATE


    @Test
    public void saveRaceTest() {
        //Exercise
        Race actual = raceService.save(race);
        //Assert
        assertTrue(race.equals(actual));
        //Teardown
    }


    //READ


    @Test
    public void getAllRacesTest() {
        //Setup
        int notExpected = 0;
        List<Race> actual = raceService.findAllRaces();
        //Exercise
        //Assert
        assertEquals(raceList.size(), actual.size());
        //Teardown
    }

    @Test
    public void getRaceByIdTest() {
        //Setup
        Race expected = race;
        //Exercise
        Race actual = raceService.findRaceById(race.getId());
        //Assert
        assertEquals(expected.hashCode(), actual.hashCode());
        //Teardown
    }


    //UPDATE

    @Test
    public void updateRaceTest() {
        //Setup
        race.setName("Indi 500");
        //Exercise
        Race actual = raceService.updateRaceById(race.getId(), race);
        //Assert
        assertEquals(race.getName(), actual.getName());
        //Teardown
    }


    // DELETE


    @Test
    public void deleteRaceTest() {
        //Setup
        Long id = race.getId();
        //Exercise
        raceService.deleteRaceById(id);
        //Assert
        assertThrows(RuntimeException.class, () -> raceService.findRaceById(id));
        //Teardown
    }

}
