package com.example.springfullproject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsertypelinkRepository extends JpaRepository<Usertypelink, String> {

}