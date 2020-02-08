package edu.northeastern.cs5200.controllers.hello;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Boot allows interacting with a database using the Java Persitence API (JPA) implemented through Spring's JpaRepository class.
 * JPA allows interacting with a database saving and retrieving Java object instances, each representing records in a table in the database.
 * To use JPA you need to create your own class that extends JpaRepository and configures the Java class and its primary key type.
 */
public interface HelloRepository extends JpaRepository<HelloObject, Integer> { }

