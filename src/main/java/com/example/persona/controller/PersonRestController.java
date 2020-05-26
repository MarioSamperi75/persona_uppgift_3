package com.example.persona.controller;


import com.example.persona.domain.Person;
import com.example.persona.domain.Response;
import com.example.persona.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

    // hämta en person utifrån dess ID + hateoas links
    @RequestMapping(value = "/personByID/{id}")
    public Optional<Person> findPersonbyID(@PathVariable long id) {
        Optional<Person> result = personService.findPerson(id);


        //get() för att kunna använda haslink
        //isPresent för att undvika exception om id inte finns
        if (result.isPresent()) {
            if (!result.get().hasLink("all_books")) {
                Link link1 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonRestController.class).findAll()).withRel("all_people");
                result.get().add(link1);
            }

            if (!result.get().hasLink("delete_book")) {
                Link link1 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonRestController.class).deleteByID(id)).withRel("delete_person");
                result.get().add(link1);
            }

            if (!result.get().hasLink("add_book")) {
                Link link1 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonRestController.class).addUser(result.get())).withRel("add_person");
                result.get().add(link1);
            }
        }
        return  result;
    }






    // hämta alla personer
    @RequestMapping(value = "/all")
    public List<Person> findAll() {
        return  personService.listAll();
    }


    // ta bort en person
    //bara get utan value?
    //behöver ha nåt objekt tillbaka annars blir nullpointer när vi använder hateoas. Response går bra.
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

        //undvika items utan username och password
        if(person.getUsername()!=null && person.getPassword()!=null )
            //återanvänder rspponse som kommer från service (user added, true)
            response = personService.save(person);
        else
            response.setMessage("User and password are required fields");
        return response;


    }

}
