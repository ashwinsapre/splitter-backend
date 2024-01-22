package com.app.splitter.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAll();
    List<Item> findByOrderId(int id);

    Item save(Item o);
}
