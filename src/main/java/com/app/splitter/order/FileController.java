package com.app.splitter.order;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping
public class FileController {
    private static final String UPLOAD_DIR = "src/main/resources/uploads";
    private final OrderController orderController;
    private final ItemController itemController;

    public static int convertMonthNameToNumber(String monthName) {
        // Create a map to store month name to number mappings
        Map<String, Integer> monthMap = new HashMap<>();
        monthMap.put("jan", 1);
        monthMap.put("feb", 2);
        monthMap.put("mar", 3);
        monthMap.put("apr", 4);
        monthMap.put("may", 5);
        monthMap.put("jun", 6);
        monthMap.put("jul", 7);
        monthMap.put("aug", 8);
        monthMap.put("sep", 9);
        monthMap.put("oct", 10);
        monthMap.put("nov", 11);
        monthMap.put("dec", 12);

        // Convert month name to number (case-insensitive)
        return monthMap.getOrDefault(monthName.trim().substring(0, 3).toLowerCase(), -1);
    }
    @Autowired
    public FileController(OrderController orderController, ItemController itemController) {
        this.orderController = orderController;
        this.itemController = itemController;
    }
    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }
        System.out.println("File received");
        // Generate a unique filename to prevent overwriting existing files
        Random random = new Random();

        // Generate a random integer
        int randomNumber = random.nextInt(Integer.MAX_VALUE);

        // Build the Path object for the destination file
        Path destination = Path.of(UPLOAD_DIR, randomNumber+"");

        try {
            // Save the file to the destination path
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File saved");
            String pythonCommand = "python3";
            String pythonScript = "src/main/resources/uploads/parse.py";

            // Add the file path as a command-line argument
            ProcessBuilder processBuilder = new ProcessBuilder(pythonCommand, pythonScript, destination.toString());

            // Redirect the output to the console
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Wait for the process to finish
            int exitCode = process.waitFor();

            // Check the exit code (0 typically indicates success)
            if (exitCode == 0) {
                System.out.println(output);
                String orderDate = output.toString().strip().split("/")[0];
                System.out.println("date="+orderDate);
                String orderId = output.toString().split("/")[1].split("-")[1].strip();
                String[] dateParts = orderDate.split(",");
                int year = Integer.parseInt(dateParts[1].strip());
                System.out.println("year="+year);
                int month = convertMonthNameToNumber(dateParts[0].split(" ")[0]);
                System.out.println("month="+month);
                int day = Integer.parseInt(dateParts[0].split(" ")[1].strip());
                System.out.println("day="+day);
                LocalDate d = LocalDate.of(year, month, day);

                orderController.insertData(new Order(Long.parseLong(orderId), d));

                ResponseEntity<List<Item>> itemsResponse = itemController.readJson(UPLOAD_DIR+"/"+randomNumber+"_items.json");
                List<Item> items = itemsResponse.getBody();
                for (Item item : items) {
                    System.out.println("inserting"+item.getName());
                    Item i = new Item(Long.parseLong(orderId), item.getName(), item.getPrice(), item.getQuantity());
                    itemController.insertItem(i);
                }

            } else {
                System.err.println("Failed to execute Python script");
            }

            return ResponseEntity.ok("File uploaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to process the uploaded file");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}