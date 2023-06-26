package com.example.springfullproject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserdetailRepository extends JpaRepository<Userdetail, String> {
    @Query(value = "select * from userdetails u where u.email=?1", nativeQuery = true)
    public Optional<Userdetail> findByEmail(String email);
}