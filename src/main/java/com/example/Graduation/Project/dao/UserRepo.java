package com.example.Graduation.Project.dao;

import com.example.Graduation.Project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User , Long> {
   @Query("select u from User u where u.email=:userEmail")
    Optional<User> findByEmail(String userEmail);
}
