package com.example.persona.controller;

import com.example.persona.domain.Person;
import com.example.persona.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class PersonController {
    private PersonService personService;


//--------------------------------------

    public PersonController() {}

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

//--------------------------------------

    @GetMapping("/")
    public String GoToHomePage(Model model) {
        List<Person> personList = personService.listAll();
        model.addAttribute("listPersons", personList);
        return "index";
    }

    @GetMapping("/new")
    public String showNewProductPage(Model model) {
        Person person = new Person();
        model.addAttribute("theperson", person);
        return "new_person";
    }

    @PostMapping("/save")
    public String savePerson(Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "error";
        personService.saveNewPerson(person);
        return "redirect:/";
    }

    //samma som save, men anropar personService.update
    //personService.update sparar utan att kolla om user redan finns
    @PostMapping("/update")
    public String updatePerson(Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "error";
        personService.updatePerson(person);
        return "redirect:/";
    }


    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable int id, Model model) {
        Optional<Person> person = personService.findPerson(id);
        if (person.isPresent()) {
            model.addAttribute("person_to_edit", person.get());
            return "edit_person";
        } else
            return "error";
    }

    @GetMapping("/delete/{id}")
    public String deletePerson(@PathVariable long id) {
        personService.delete(id);
        return "redirect:/";
    }


}
