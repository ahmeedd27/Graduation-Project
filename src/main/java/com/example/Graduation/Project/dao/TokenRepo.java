package com.example.Graduation.Project.dao;

import com.example.Graduation.Project.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<Token , Long> {
    @Query("SELECT t FROM Token t WHERE t.token=:jwt")
    Optional<Token> findTokenByName(String jwt);

    @Query("select t from Token t where t.user.id=:id")
    List<Token> findAllTokensByUserId(Long id);
}
