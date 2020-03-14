package edu.northeastern.cs5200.repositories;

import edu.northeastern.cs5200.modules.Faculty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FacultyRepository extends CrudRepository<Faculty,Integer> {

    @Query("select p from Person p where p.firstName=:firstName")
    public Faculty findByName(@Param("firstName") String firstName);

}
