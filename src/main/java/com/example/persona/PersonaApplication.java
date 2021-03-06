package com.example.persona;

import com.example.persona.domain.Person;
import com.example.persona.repository.PersonRepository;
import com.example.persona.service.PersonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class PersonaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonaApplication.class, args);
    }


    @Bean
    public CommandLineRunner setUp(PersonService personService){
        return  (args -> {
            Person person1 = new Person (   "marioxxx","mariosss123","Mario","Samperi", "Italy", 1975);
            Person person2 = new Person (   "marioyyy","mariosss456","Mario","Samperi", "Italy", 1975);
            Person person3 = new Person (   "mariozzz","mariosss789","Mario","Samperi", "Italy", 1975);


            personService.saveNewPerson(person1);
            personService.saveNewPerson(person2);
            personService.saveNewPerson(person3);
        });
    }

}
