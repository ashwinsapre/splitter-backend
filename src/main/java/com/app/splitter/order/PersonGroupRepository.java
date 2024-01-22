package com.app.splitter.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonGroupRepository extends JpaRepository<PersonGroup, Long> {
    List<PersonGroup> findAll();
    Optional<PersonGroup> findById(int id);
    PersonGroup save(PersonGroup p);
}
