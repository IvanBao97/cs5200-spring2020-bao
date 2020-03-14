package edu.northeastern.cs5200.repositories;

import edu.northeastern.cs5200.modules.Enrollment;
import org.springframework.data.repository.CrudRepository;

public interface EnrollmentRepository extends CrudRepository<Enrollment,Integer> {

}
