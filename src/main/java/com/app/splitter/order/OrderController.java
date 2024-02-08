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
        Optional<Order> orderOptional = orderRepository.findByOrderId(orderId);

        String d = String.valueOf(orderOptional.get().getDate());
        List<Item> itemsResponse = itemRepository.findByOrderId(orderId);
        itemsResponse.add(0, new Item(0, d, 0, 0));
        if (!itemsResponse.isEmpty()) {
            return ResponseEntity.ok(itemsResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/removeOrder/{orderId}")
    @Transactional
    public ResponseEntity<Void> removeOrder(@PathVariable String orderId) {
        Long orderIdLong = Long.parseLong(orderId);
        Optional<Order> optionalOrder = orderRepository.findByOrderId(orderIdLong);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (!order.isArchived()){
                order.setArchived(true);
            }
            else {
                order.setArchived(false);
            }
            orderRepository.save(order);
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
