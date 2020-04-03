package com.galvanize.services;

import com.galvanize.entites.Race;
import com.galvanize.repository.RaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaceService {

    private RaceRepository raceRepository;

    public RaceService(RaceRepository raceRepository){
        this.raceRepository = raceRepository;
    }

    //CREATE


    public Race save(Race race) {
        if(race != null){
            return raceRepository.save(race);
        }else{
            throw new RuntimeException("race should not be null");
        }
    }


    //READ


    public List<Race> findAllRaces() {
        return raceRepository.findAll();
    }

    public Race findRaceById(Long id) {
        if(raceRepository.findById(id).isPresent()){
            return raceRepository.findById(id).get();
        }else{
            throw new RuntimeException("Race id not found");
        }
    }


    //UPDATE


    public Race updateRaceById(Long id, Race race) {
        if(raceRepository.findById(id).isPresent()){
            raceRepository.save(race);
            return raceRepository.findById(id).get();
        }else{
            throw new RuntimeException("Race id not found");
        }
    }


    //DELETE


    public void deleteRaceById(Long id) {
        if(raceRepository.findById(id).isPresent()){
            raceRepository.deleteById(id);
        }else{
            throw new RuntimeException("Race id not found");
        }

    }
}
