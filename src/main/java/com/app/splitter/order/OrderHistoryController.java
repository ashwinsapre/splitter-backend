package com.app.splitter.order;

import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/saveOrder")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class OrderHistoryController {
    private final OrderHistoryRepository orderHistoryRepository;
    @Autowired
    public OrderHistoryController(OrderHistoryRepository orderHistoryRepository){
        this.orderHistoryRepository = orderHistoryRepository;
    }

    @PostMapping
    @Transactional
    public void save(@RequestBody Map<String, Object> request){

        int orderID = Integer.parseInt((String) request.get("orderID"));
        List<Map<String, Object>> orderDataList = (List<Map<String, Object>>) request.get("items");

        for (Map<String, Object> orderData : orderDataList) {
            // Access individual values using keys
            String itemName = (String) orderData.get("itemName");
            int personId = (int) orderData.get("person_id");
            float itemPrice = ((Number) orderData.get("item_price")).floatValue();
            float quantity =  ((Number)orderData.get("quantity")).floatValue();

            // Check if the item exists in the database
            Optional<OrderHistory> existingOrder = orderHistoryRepository.findByOrderIdAndItemNameAndPersonId(orderID, itemName, personId);

            if (existingOrder.isPresent()) {
                // If item exists, compare itemPrice and quantity
                OrderHistory existingEntry = existingOrder.get();
                if (existingEntry.getItem_price() != itemPrice || existingEntry.getItem_qty() != quantity) {
                    // If itemPrice or quantity has changed, update the entry
                    existingEntry.setItem_price(itemPrice);
                    existingEntry.setItem_qty(quantity);
                    orderHistoryRepository.save(existingEntry);
                }
            } else {
                // If item doesn't exist, insert it
                orderHistoryRepository.save(new OrderHistory(orderID, personId, itemName, itemPrice, quantity));
            }
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<OrderHistory>> getOrderDetails(@PathVariable int id){
        List<OrderHistory> response = orderHistoryRepository.findByOrderId(id);
        if (!response.isEmpty()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
