package edu.northeastern.cs5200.repositories;

import edu.northeastern.cs5200.modules.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends CrudRepository<Student,Integer> {

    @Query("select p from Person p where p.firstName=:firstName")
    public Student findByName(@Param("firstName") String firstName);

}
