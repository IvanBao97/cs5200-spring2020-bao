package edu.northeastern.cs5200.modules;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Faculty extends Person {
    private String office;
    private boolean tenured;

    @OneToMany(mappedBy = "faculty", fetch= FetchType.EAGER)
    private List<Course> courses;

    public Faculty() { }

    public Faculty(String firstName, String lastName, String username, String password, String office, boolean tenured) {
        super.setUsername(username);
        super.setPassword(password);
        super.setFirstName(firstName);
        super.setLastName(lastName);
        this.office = office;
        this.tenured = tenured;
    }

    public void authoredCourse(Course course) {
        this.courses.add(course);
        if(course.getFaculty() != this)
            course.setFaculty(this);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public boolean isTenured() {
        return tenured;
    }

    public void setTenured(boolean tenured) {
        this.tenured = tenured;
    }
}
