package com.app.splitter.order;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ItemService {
    private final ObjectMapper objectMapper;
    @Autowired
    public ItemService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    public List<Item> readJsonFile(String filePath) throws IOException {
        // Read JSON from file into an object
        return objectMapper.readValue(new File(filePath), new TypeReference<List<Item>>() {});
    }
}
