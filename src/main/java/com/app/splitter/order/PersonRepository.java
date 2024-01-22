package com.app.splitter.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findAll();
    Optional<Person> findById(int id);
    Person save(Person p);
}
