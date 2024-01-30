package com.app.splitter.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.app.splitter.order.OrderService;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

//controls /orders route

@RestController
@RequestMapping(path="/orders")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
public class OrderController {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    @GetMapping
    //orders endpoint returns a list of all orders
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> orders = orderRepository.findAll();
        if (!orders.isEmpty()) {
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{id}")
    //orders/{id} returns details of a specific order
    public ResponseEntity<List<Item>> getOrderDetails(@PathVariable String id) {
        int orderId = Integer.parseInt(id);
        List<Item> itemsResponse = itemRepository.findByOrderId(orderId);
        if (!itemsResponse.isEmpty()) {
            return ResponseEntity.ok(itemsResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/removeOrder/{orderId}")
    @Transactional
    public ResponseEntity<Void> removeOrder(@PathVariable String orderId) {
        int orderIdInt = Integer.parseInt(orderId);
        System.out.println(orderIdInt);
        if (orderRepository.existsByOrderId(orderIdInt)) {
            // Implement logic to remove associated items or any additional cleanup
            orderRepository.deleteByOrderId(orderIdInt);
            itemRepository.deleteByOrderId(orderIdInt);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public void insertData(@RequestBody Order o) {
        System.out.println(o.getOrderId());
        System.out.println(o.getDate());
        orderRepository.save(o);
    }
}
