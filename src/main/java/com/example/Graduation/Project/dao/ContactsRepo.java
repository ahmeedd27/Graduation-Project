package com.example.Graduation.Project.dao;

import com.example.Graduation.Project.model.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactsRepo extends JpaRepository<Contacts , Long> {
}
