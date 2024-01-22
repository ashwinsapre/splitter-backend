package com.app.splitter.order;

import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;

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
        orderHistoryRepository.deleteByOrderId(orderID);

        for (Map<String, Object> orderData : orderDataList) {
            // Access individual values using keys
            String itemName = (String) orderData.get("itemName");
            int personId = (int) orderData.get("person_id");
            float itemPrice = ((Number) orderData.get("item_price")).floatValue();
            float quantity =  ((Number)orderData.get("quantity")).floatValue();

            orderHistoryRepository.save(new OrderHistory(orderID, personId, itemName, itemPrice, quantity));
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
