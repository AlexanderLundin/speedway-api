package com.galvanize.services;

import com.galvanize.entites.Car;
import com.galvanize.entites.Driver;
import com.galvanize.entites.Model;
import com.galvanize.entites.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DriverServiceTest {


    @Autowired
    DriverService driverService;


    private Driver driver;
    private Car car;

    @BeforeEach
    public void setup() {
        //Setup
        String firstName = "name";
        String lastName = "name2";
        String nickName = "nickName";
        driver = new Driver(firstName, lastName, Date.valueOf(LocalDate.of(1990,11,16)), nickName);
        driverService.save(driver);
        car = new Car("Green Hornet", Model.Alpine, "1960", Status.AVAILABLE, 198L);
    }


    //CREATE


    @Test
    public void saveDriverTest() {
        //Exercise
        Driver actual = driverService.save(driver);
        //Assert
        assertEquals(driver.getAge(), actual.getAge());
        assertEquals(driver.getNickName(), actual.getNickName());
        //Teardown
    }


    //READ


    @Test
    public void getAllDriversTest() {
        //Setup
        int notExpected = 0;
        List<Driver> drivers = driverService.findAllDrivers();
        int actual = drivers.size();
        //Exercise
        //Assert
        assertNotEquals(notExpected, actual);
        //Teardown
    }

    @Test
    public void getDriverByIdTest() {
        //Setup
        Driver expected = driver;
        //Exercise
        Driver actual = driverService.findDriverById(driver.getId());
        //Assert
        assertEquals(expected.hashCode(), actual.hashCode());
        //Teardown
    }


    //UPDATE

    @Test
    public void updateDriverTest() {
        //Setup
        driver.setBirthDate(Date.valueOf(LocalDate.of(1991, 11, 16)));
        driver.setCar(car);
        driver.setFirstName("Batman");
        driver.setLastName("Spiderman");
        driver.setNickName("MickyMouse");
        //Exercise
        Driver driver2 = driverService.updateDriverById(driver.getId(), driver);
        Car expected = car;
        Car actual = driver2.getCar();
        //Assert
        assertEquals(expected.hashCode(), actual.hashCode());
        //Teardown
    }


    // DELETE


    @Test
    public void deleteDriverTest() {
        //Setup
        Long id = driver.getId();
        //Exercise
        driverService.deleteDriverById(id);
        //Assert
        assertThrows(RuntimeException.class, () -> driverService.findDriverById(id));
        //Teardown
    }

}
