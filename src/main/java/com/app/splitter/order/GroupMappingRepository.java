package com.app.splitter.order;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

public interface GroupMappingRepository extends JpaRepository<GroupMapping, String> {
    List<GroupMapping> findAll();
    Optional<GroupMapping> findByGroupId(long id);
    Optional<GroupMapping> findByPersonId(long id);
    GroupMapping save(GroupMapping p);
    void deleteByGroupId(long id);
}

