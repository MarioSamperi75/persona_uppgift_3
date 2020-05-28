package com.example.persona.service;

import com.example.persona.domain.Person;
import com.example.persona.domain.Response;
import com.example.persona.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private PersonRepository personRepository;
    //private static final Logger log = LoggerFactory.getLogger(PersonService.class);

//--------------------------------------
    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

//--------------------------------------
    // save får en check och går vidare bara om person INTE finns
    // detta går inte bra för action update: personen finns!
    // då får action update en ny metod (updatePerson) utan check.


    @Transactional
    public Response updatePerson(Person person) {
            Response response = new Response("User uppdated", true);
            personRepository.save(person);
        return response;
    }


    @Transactional
    public Response saveNewPerson(Person person) {
        Response response =  checkIfUsernameAlreadyExists(person.getUsername());

        if (response.isStatus()==false) {
            personRepository.save(person);
            response.setMessage("User added");
            response.setStatus(true);
        }
        return response;
    }

    public Response checkIfUsernameAlreadyExists (String username) {
        Response response = new Response("Username already exists", false);
        List<Person> people = personRepository.findAll();
        if (people.size()>0) {                      //is database empty?
            for (Person person : people) {          // check if Username alredy exists
                if (person.getUsername().equalsIgnoreCase(username)) {
                    response.setStatus(true);
                    return response;
                }
            }
        }
        return response;
    }

    public List<Person> listAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findPerson(long id) {
        return personRepository.findById(id);
    }

    @Transactional
    public Response delete(long id) {
        Response response = new Response("Person deleted", true);
        personRepository.deleteById(id);
        return response;
    }

}
