package ru.sonyabeldy.springcourse.Project2Boot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sonyabeldy.springcourse.Project2Boot.models.Book;
import ru.sonyabeldy.springcourse.Project2Boot.models.Person;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT b FROM Book b WHERE b.name LIKE %?1%")
    public List<Book> search(String q);


    List<Book> findByOwner(Person owner);
    List<Book> findByName(String name);
}
