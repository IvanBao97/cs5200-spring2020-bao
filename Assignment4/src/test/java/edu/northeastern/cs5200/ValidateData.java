package edu.northeastern.cs5200;

import edu.northeastern.cs5200.daos.UniversityDao;
import edu.northeastern.cs5200.modules.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidateData {
    @Autowired
    UniversityDao ud;

    @Test
    public void testValidateUsers() {
        int n = 9;
        List<Person> users = ud.findAllPersons();
        assert(n==users.size()) : "Total number doesn't match";
    }

    @Test
    public void testValidateFaculty() {
        int n = 2;
        List<Faculty> users = ud.findAllFaculty();
        assert(n==users.size()) : "Total number doesn't match";
    }

    @Test
    public void testValidateStudent() {
        int n = 7;
        List<Student> users = ud.findAllStudents();
        assert(n==users.size()) : "Total number doesn't match";
    }

    @Test
    public void testValidateCourse() {
        int n = 4;
        List<Course> users = ud.findAllCourses();
        assert(n==users.size()) : "Total number doesn't match";
    }

    @Test
    public void testValidateSection() {
        int n = 4;
        List<Section> users = ud.findAllSections();
        assert(n==users.size()) : "Total number doesn't match";
    }

    @Test
    public void testValidateAuthor() {
        List<Faculty> faculties = ud.findAllFaculty();
        for(Faculty f:faculties) {
            System.out.printf("Faculty "+ f.getFirstName()+" "+f.getLastName()+
                    " authors %d courses.%n",f.getCourses().size());
        }
    }

    @Test
    public void testSectionPerCourse() {
        List<Course> courses = ud.findAllCourses();
        for(Course c:courses) {
            System.out.printf("Course "+c.getLabel()+" has %d sections%n", c.getSections().size());
        }
    }

    @Test
    public void testStudentPerSection() {
        List<Section> sections = ud.findAllSections();
        for(Section s:sections) {
            System.out.printf("Section "+s.getTitle()+" is enrolled by %d students%n", s.getEnrollments().size());
        }
    }

    @Test
    public void testSectionPerStudent() {
        List<Student> students = ud.findAllStudents();
        for(Student s:students) {
            System.out.printf("Student "+s.getFirstName()+" "+s.getLastName()+" has %d sections%n", s.getEnrollments().size());
        }
    }

    @Test
    public void testSeatsPerSection() {
        List<Section> sections = ud.findAllSections();
        for(Section s:sections) {
            System.out.printf("Section "+s.getTitle()+"has %d seats%n", s.getSeats());
        }
    }
}
