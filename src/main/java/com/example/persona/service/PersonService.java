package com.example.persona.service;

import com.example.persona.domain.Person;
import com.example.persona.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private PersonRepository personRepository;

    //--------------------------------------
    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

//--------------------------------------

    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    public List<Person> listAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findPerson(long id) {
        return personRepository.findById(id);
    }

    @Transactional
    public void delete(long id) {
        personRepository.deleteById(id);
    }

}
