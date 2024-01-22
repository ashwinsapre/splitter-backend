package com.app.splitter.order;

import com.app.splitter.order.Item;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    private final ObjectMapper objectMapper;

    public OrderService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Item> readJsonFile(String filePath) throws IOException {
        // Read JSON from file into an object
        return objectMapper.readValue(new File(filePath), new TypeReference<List<Item>>() {});
    }

}
