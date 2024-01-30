package com.app.splitter.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static java.lang.Math.abs;

@RestController
@RequestMapping("/person")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
public class PersonController {

    private final PersonRepository personRepository;
    private final OrderHistoryRepository orderHistoryRepository;
    @Autowired
    public PersonController(PersonRepository personRepository, OrderHistoryRepository orderHistoryRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
        this.personRepository = personRepository;
    }
    @GetMapping
    //orders endpoint returns a list of all orders
    public ResponseEntity<List<Person>> getPersons() {
        List<Person> persons = personRepository.findAll();
        if (!persons.isEmpty()) {
            return ResponseEntity.ok(persons);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<String> handlePersonCreate(@RequestParam("name") String name) {
        System.out.println("Name received");
        // Generate a random ID
        Random random = new Random();
        int randomNumber = abs(random.nextInt());
        this.insertData(new Person(name, randomNumber));
        return ResponseEntity.ok("Person created successfully!");
    }
    @GetMapping("/{id}")
    //persons/{id} returns order history of a specific person
    public ResponseEntity<List<Object[]>> getPersonDetails(@PathVariable String id) {
        int i= Integer.parseInt(id);
        List<Object[]> resp = orderHistoryRepository.findPersonTotalByPersonId(i);
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/removePerson/{personId}")
    public ResponseEntity<Void> removePerson(@PathVariable String personId) {
        int personIdInt = Integer.parseInt(personId);
        System.out.println("trying to remove person id="+personIdInt);
//        if (orderRepository.existsById(orderIdInt)) {
//            // Implement logic to remove associated items or any additional cleanup
//            orderRepository.deleteById(orderIdInt);
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
        return ResponseEntity.noContent().build();
    }
    public void insertData(@RequestBody Person p) {
        personRepository.save(p);
    }
}
