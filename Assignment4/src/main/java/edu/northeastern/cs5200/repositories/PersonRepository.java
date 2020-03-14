package edu.northeastern.cs5200.repositories;

import edu.northeastern.cs5200.modules.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person,Integer> {
}
