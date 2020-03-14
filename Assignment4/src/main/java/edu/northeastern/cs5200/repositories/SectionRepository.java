package edu.northeastern.cs5200.repositories;

import edu.northeastern.cs5200.modules.Section;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SectionRepository extends CrudRepository<Section,Integer> {

    @Query("select s from Section s where s.title=:title")
    public Section findByTitle(@Param("title")String title);

}
