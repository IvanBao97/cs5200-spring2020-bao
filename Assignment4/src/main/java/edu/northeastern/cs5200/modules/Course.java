package edu.northeastern.cs5200.modules;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String label;

    @OneToMany(mappedBy="course", fetch=FetchType.EAGER)
    private List<Section> sections = new ArrayList<Section>();

    @ManyToOne(cascade = CascadeType.MERGE)
    private Faculty faculty;

    public void addSections(Section section) {
        this.sections.add(section);
        if(section.getCourse()!=this)
            section.setCourse(this);
    }



    public Course() {}
    public Course(String label) {
        super();
        this.label = label;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
        if(!faculty.getCourses().contains(this)) {
            faculty.getCourses().add(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}