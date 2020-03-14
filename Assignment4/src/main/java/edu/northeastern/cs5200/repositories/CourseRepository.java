package edu.northeastern.cs5200.repositories;

import edu.northeastern.cs5200.modules.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends CrudRepository<Course,Integer> {

    @Query("select c from Course c where c.label=:label")
    public Course findByLabel(@Param("label") String label);

}
