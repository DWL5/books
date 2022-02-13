package com.example.react_srpingboot_aws.repository;

import com.example.react_srpingboot_aws.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, String> {

    List<Todo> findByUserId(String userId);
}
