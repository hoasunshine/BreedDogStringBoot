package com.hoasun.controller;

import com.hoasun.entity.Dog;
import com.hoasun.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping(path = "/dogs")
public class DogController {
    private static ArrayList<Dog> list = new ArrayList<>();
    @Autowired
    private DogRepository dogRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Dog>> getList() {
        return new ResponseEntity<>(dogRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Dog getDetail(@PathVariable int id) {
        return dogRepository.findById(id).orElse(null);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Dog> create(@RequestBody Dog dog) {
        Dog save = dogRepository.save(dog);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Dog dog) {
        Optional<Dog> optionalDog = dogRepository.findById(id);
        if (optionalDog.isPresent()) {
            Dog existDog = optionalDog.get();
            existDog.setName(dog.getName());
            existDog.setBreedName(dog.getBreedName());
            existDog.setBirthday(dog.getBirthday());
            existDog.setGender(dog.getGender());
            existDog.setColor(dog.getColor());
            existDog.setStatus(dog.getStatus());
            dogRepository.save(existDog);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        Optional<Dog> optionalDog = dogRepository.findById(id);
        if (optionalDog.isPresent()) {
            dogRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
