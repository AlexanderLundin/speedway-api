package com.galvanize.controllers;

import com.galvanize.entites.Race;
import com.galvanize.responses.Response;
import com.galvanize.services.RaceService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RaceController {

    RaceService raceService;

    public RaceController(RaceService raceService){
        this.raceService = raceService;
    }


    // CREATE


    @PostMapping(value = "/races", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response postRace(@RequestBody Race race){
        race = raceService.save(race);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");
        return new Response(race , headers, HttpStatus.OK);

    }


    //READ

    @GetMapping("/races")
    public Response getAllRaces(){
        List<Race> races = raceService.findAllRaces();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");
        return new Response(races , headers, HttpStatus.OK);
    }

    @GetMapping("/races/{id}")
    public Response getAllOrderById(@PathVariable long id){
        Race race = raceService.findRaceById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");
        return new Response(race , headers, HttpStatus.OK);
    }


    //UPDATE


    @PutMapping("/races/{id}")
    public Response updateRaceById(@PathVariable long id, @RequestBody Race race){
        race = raceService.updateRaceById(id, race);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");
        return new Response(race , headers, HttpStatus.OK);
    }


    //DELETE


    @DeleteMapping("/races/{id}")
    public void deleteRaceById( @PathVariable Long id){
        raceService.deleteRaceById(id);
    }
}
