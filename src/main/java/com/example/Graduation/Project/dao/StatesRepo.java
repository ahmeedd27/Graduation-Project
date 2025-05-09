package com.example.Graduation.Project.dao;

import com.example.Graduation.Project.model.States;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatesRepo extends JpaRepository<States , Long> {

    @Query("SELECT s FROM States s WHERE s.address=:location AND s.price=:price AND s.rooms=:numberOfRooms")
    States getState(String location, double price, int numberOfRooms);
}
