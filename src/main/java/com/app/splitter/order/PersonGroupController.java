package com.app.splitter.order;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import static java.lang.Math.abs;

@RestController
@RequestMapping("/groups")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
public class PersonGroupController {
    private final PersonGroupRepository personGroupRepository;
    private final GroupMappingRepository groupMappingRepository;

    @Autowired
    public PersonGroupController(PersonGroupRepository personGroupRepository, GroupMappingRepository groupMappingRepository){
        this.personGroupRepository = personGroupRepository;
        this.groupMappingRepository = groupMappingRepository;
    }

    @GetMapping
    public ResponseEntity<List<PersonGroup>> getGroups(){
        List<PersonGroup> groups = personGroupRepository.findAll();
        if (!groups.isEmpty()) {
            return ResponseEntity.ok(groups);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<String> handleGroupCreate(@RequestParam("name") String name) {
        System.out.println("Group Name received");
        // Generate a random ID
        Random random = new Random();
        int randomNumber = abs(random.nextInt());
        personGroupRepository.save(new PersonGroup(randomNumber, name));
        return ResponseEntity.ok("Group created successfully!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PersonGroup>> getGroup(@PathVariable String id) {
        long i= Long.parseLong(id);
        Optional<PersonGroup> resp = personGroupRepository.findById(i);
        return ResponseEntity.ok(resp);
    }
    @PostMapping("/saveGroup")
    @Transactional
    public void createGroup(@RequestBody Map<String, Object> request) {
        System.out.println("trying to update groups");
        List<Map<String, Object>> groups = (List<Map<String, Object>>) request.get("groups");

        for (Map<String, Object> orderData : groups) {
            int groupId = Integer.parseInt((String) orderData.get("groupId"));
            groupMappingRepository.deleteByGroupId(groupId);
            List<Integer> personIds = (List<Integer>) orderData.get("personIds");
            for (int pid : personIds) {
                groupMappingRepository.save(new GroupMapping(pid, groupId));
            }
        }
    }
    @DeleteMapping("/removeGroup/{groupId}")
    public ResponseEntity<Void> removeOrder(@PathVariable String groupId) {
        Long groupIdInt = Long.parseLong(groupId);
        System.out.println("trying to remove group id="+groupIdInt);
        if (personGroupRepository.existsById(groupIdInt)) {
            // Implement logic to remove associated items or any additional cleanup
            personGroupRepository.deleteById(groupIdInt);
            groupMappingRepository.deleteByGroupId(groupIdInt);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get")
    public ResponseEntity<List<GroupMapping>> getAllGroupMembers() {
        List<GroupMapping> resp = groupMappingRepository.findAll();
        return ResponseEntity.ok(resp);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Optional<GroupMapping>> getGroupMembers(@PathVariable String id) {
        int i= Integer.parseInt(id);
        Optional<GroupMapping> resp = groupMappingRepository.findByGroupId(i);
        return ResponseEntity.ok(resp);
    }
}
