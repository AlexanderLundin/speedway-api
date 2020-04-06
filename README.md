# speedway-api

# Steps to build with docker

Retrieve the image from docker hub with docker toolbox or other docker CLI

$ docker pull alundin/speedway

$ docker run -p 8080:8080 --rm alundin/speedway

$ docker-machine ip

Open up brower on the ip address found from command above + ":8080/api/races"

# Sample Postman Data 
## You can import this link directly with Postman desktop app

https://www.getpostman.com/collections/dafae764ccc3ed9c716e

# Instructions
Develop the app one test at a time. Always start with the simplest implementation -- just enough to make the test pass. In this way, you can thoughtfully develop the next feature and prevent getting stuck on complex things.

Make frequent commits to github so your instructors can support your work. Commits should always be small. For example, commiting code after making each test pass or after refactoring a section of code. Only push working code to the project repo, which means all tests in the repo should be green and the app should start without error if your instructor downloads it.

Your app should employ all the tools available to you that we've learned about in the course so far:

Spring
JPA and MySQL
Repositories, entities, controllers and services.
Class objects, including interfaces and enumerations.
All responses must return the following information:

The status code of the response
The status message for the response
The data requested: lists must be an array and individual items must be an object/hash
Example successful response:

    {
        "status": "OK",
        "status_code": 200,
        "data": [
            {
                "id": 43,
                "nickname": "The Condor",
                "model": "Corvette",
                "year": "2019",
                "driver": {...},
                "status": "AVAILABLE",
                "top_speed": 200
            },
            {
                "id": 44,
                "nickname": "Blue Fire",
                "model": "Ferrari",
                "year": "2017",
                "driver": {...},
                "status": "UNAVAILABLE",
                "top_speed": 100
            }
        ]
    }

Example error response:

    {
        "status": "Not Found.",
        "status_code": 404,
        "data": []
    }

Example successful result for 1 item:

    {
        "status": "Not Found.",
        "status_code": 404,
        "data": {
            "id": 43,
            "nickname": "The Condor",
            "model": "Corvette",
            "year": "2019",
            "driver": {...},
            "status": "AVAILABLE",
            "top_speed": 200
        }
    }

# Cars...
have a nickname and model
have a Driver.
have a status: available, unavailable
have a top speed
have a category which can be one of the following:
can convert between miles per hour (mph) and kilometers per hour (kph)
Cars come in the following models: Alpine, Ferrari, Maserati, Porsche, Nissan and Jaguar.

An example of a car object:

    {
        "id": 43,
        "nickname": "The Condor",
        "model": "Corvette",
        "year": "2019",
        "driver": {...},
        "status": "AVAILABLE",
        "top_speed": 200
    }
  
Drivers...
have a first name, last name, nickname, age and birthdate
have a Car
have wins and losses (cannot be changed via the client)
STRETCH GOAL: have records (RaceRecord), contains information about the results of each race. (cannot be changed via client)
STRETCH GOAL: have a sponsor.
An example of a driver object:

    {
        "id": 332,
        "first_name": "Maria",
        "last_name": "Lopez",
        "age": 22,
        "nickname": "The Blaze",
        "car": {...},
        "records": [...],
        "wins": 4,
        "losses": 1
    }
  
Race...
have a name, date, and best time (minutes, seconds, millseconds).
have participants (Drivers)
have a winner (a Driver).
have a category
STRETCH GOAL: have a Location.
Races have the following categories: open wheel, tour, sport car, rally, time attack, drag, off-road, stock car

An example of a race object:

    {
        "id": 17,
        "name": "Grand Prix III",
        "category": "stock car",
        "date": "2020-06-03",
        "bestTime": "03:36:78",
        "winner": {...},
        "participants": [...]
    }
  
Stretch Goals
If you complete the API before it's due, add any of the following features.

Location resource: Trials occur at specific locations and locations can host many trials.
RaceRecord: Drivers have records which contain information about their wins, losses, best time, and best speed. Records cannot be queried via the API.
Search endpoint: A client can search for cars, drivers or races using any of their properties.
Sponsor: Drivers have sponsors and sponsors can have many drivers. Sponsors cannot be queried via the API.
Spec: Cars have specs which contain info about their brakes, wheels, body. Specs cannot be queried via the API.
Locations ...
have a name
have country, city, and state/province (can be null if lat/long provided)
have latitude and longitude (can be null if country, city, state provided)
RaceRecord ...
belongs to a Driver
part of a Race
