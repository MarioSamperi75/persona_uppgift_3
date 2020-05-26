package com.example.persona.controller;


import com.example.persona.domain.Person;
import com.example.persona.domain.Response;
import com.example.persona.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;





@RestController
public class PersonRestController {

    private PersonService personService;
    private static final Logger log = LoggerFactory.getLogger(PersonService.class);


    public PersonRestController() {
    }

    @Autowired
    public PersonRestController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping("/provaRest")
    public String prova() {
        return "hello Rest!!";
    }

    // hämta en person utifrån dess ID
    @RequestMapping(value = "/personByID/{id}")
    public Optional<Person> findPersonbyID(@PathVariable long id) {
        return  personService.findPerson(id);
    }

    // hämta alla personer
    @RequestMapping(value = "/all")
    public List<Person> findAll() {
        return  personService.listAll();
    }


    // ta bort en person
    //bara get utan value?
    //return respons annars blir nullpointer när vi använder hateoas
    @RequestMapping(value = "/personByID/{id}/delete")
    public Response deleteByID(@PathVariable long id)
    {
        Response response=new Response("Person deleted",false);
        int indexToRemove=-1;

        List<Person> all= personService.listAll();

        for (int i = 0; i < all.size(); i++) {
            if(all.get(i).getId()==id)
                indexToRemove=i;

        }
        if(indexToRemove!=-1)
        {
            personService.delete(id);
            response.setStatus(true);
        }
        else
            response.setMessage("Not found");

        return response;
    }


    // lägga till en person.
    @PostMapping("/person/add")
    public Response addUser(@RequestBody Person person)
    {
        Response response = new Response("User added", false);
        //if((person.getId()!=0) &&(person.getUsername()!=null) && person.getPassword()!=null ) {
            response = personService.save(person);
        //}
        log.error(response.getMessage());
        return response;


    }




}

/* @RequestMapping(value = "/pidunJSON/{id}/delete",produces = "application/json")
    public Response deleteByID(@PathVariable int id)
    {
        Response response=new Response("Pidun deleted",false);
        int indexToRemove=-1;
        for (int i = 0; i < pidunList.size(); i++) {
            if(pidunList.get(i).getId()==id)
                indexToRemove=i;

        }
        if(indexToRemove!=-1)
        {
            pidunList.remove(indexToRemove);
            response.setStatus(true);
        }
        else
            response.setMessage("Not found");

        return response;
    }

    @PostMapping("/Pidun/add")
    public Response addUser(@RequestBody Pidun pidun)
    {
        Response response=new Response("Pidun added",false);
        if((pidun.getId()!=0) &&(pidun.getName()!=null) && pidun.getCost()!=0 && pidun.getPrice()!=0 ) {
            pidunList.add(pidun);
            response.setStatus(true);
        }
        else
            response.setMessage("failed to add");
        return response;

    }*/
