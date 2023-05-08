package ru.sonyabeldy.springcourse.Project2Boot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sonyabeldy.springcourse.Project2Boot.models.Person;
import ru.sonyabeldy.springcourse.Project2Boot.services.BooksService;
import ru.sonyabeldy.springcourse.Project2Boot.services.PeopleService;


@Controller
@RequestMapping("/people")
public class PeopleController {

    public final PeopleService peopleService;
    public final BooksService booksService;
    public PeopleController(PeopleService peopleService, BooksService booksService) {
        this.peopleService = peopleService;
        this.booksService = booksService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model) {
        model.addAttribute("person", peopleService.findById(id));
        model.addAttribute("books", booksService.findByOwner(peopleService.findById(id)));
        return "people/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("person", peopleService.findById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}/update")
    public String update(@PathVariable int id, @ModelAttribute("person") Person person) {
        peopleService.save(person);
        return "redirect:/people/{id}";
    }


    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("person") Person person) {
        peopleService.save(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(peopleService.findById(id));
        return "redirect:/people";
    }

}
