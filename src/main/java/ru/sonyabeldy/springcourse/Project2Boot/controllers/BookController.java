package ru.sonyabeldy.springcourse.Project2Boot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sonyabeldy.springcourse.Project2Boot.models.Book;
import ru.sonyabeldy.springcourse.Project2Boot.models.Person;
import ru.sonyabeldy.springcourse.Project2Boot.services.BooksService;
import ru.sonyabeldy.springcourse.Project2Boot.services.PeopleService;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BooksService booksService;
    private final PeopleService peopleService;

    public BookController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

//    @GetMapping
//    public String index(Model model) {
//        model.addAttribute("books", booksService.findAll());
//        return "books/index";
//    }

    @GetMapping()
    public String index(Model model, @RequestParam(value = "sort_by_year", required = false, defaultValue = "false") boolean sortByYear, @RequestParam(value = "page", required = false, defaultValue = "-1") int page, @RequestParam(value = "books_per_page", required = false, defaultValue = "-1") int booksPerPage) {
//        List<Book> bookList = page < 0 || booksPerPage < 0 ? booksService.findAll() : booksService.findAll(page, booksPerPage);
        List<Book> bookList;
        if(page < 0 || booksPerPage < 0) {
            bookList = booksService.findAll(sortByYear);
        } else {
            bookList = booksService.findAll(page, booksPerPage, sortByYear);
        }
        model.addAttribute("books", bookList);
        //page=1&books_per_page=3
//        ?page=1&books_per_page=3&sort_by_year=true
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, @ModelAttribute("person") Person person, Model model) {
        model.addAttribute("book", booksService.findById(id));
        model.addAttribute("people", peopleService.findAll());

        model.addAttribute("owner", Optional.ofNullable(booksService.findById(id).getOwner()));
        return "books/show";
    }

    @PatchMapping("/{id}")
    public String setOwner(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        booksService.setOwner(id, person);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/remove")
    public String removeOwner(@PathVariable("id") int id) {
        booksService.removeOwner(id);
        return "redirect:/books/{id}";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") Book book) {
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.findById(id));
        return "books/edit";
    }
    @PatchMapping("/{id}/update")
    public String update(@ModelAttribute("book") Book book) {
        booksService.save(book);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

//    @GetMapping("/search/s")
//    public String search(@PathVariable("word") String word, @ModelAttribute("searchRequest") String searchRequest) {
//        List<Book> bookList = booksService.searchByName(searchRequest);
//
//        if(!bookList.isEmpty()) {
//            System.out.println(bookList.get(0));
//        }
//        return "books/search";
//    }

    @GetMapping("/search")
    public String searchView(@RequestParam(value = "q", required = false) String q, Model model) {
        List<Book> bookList = null;
        if (!Objects.equals(q, "")) {
            bookList = booksService.searchByName(q);
        }
        model.addAttribute("books", bookList);

        return "books/search";
    }


}
