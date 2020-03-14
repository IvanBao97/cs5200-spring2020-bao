package edu.northeastern.cs5200.daos;

import edu.northeastern.cs5200.modules.*;
import edu.northeastern.cs5200.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UniversityDao {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    SectionRepository sectionRepository;
    @Autowired
    EnrollmentRepository enrollmentRepository;


    public void truncateDatabase() {
        enrollmentRepository.deleteAll();
        sectionRepository.deleteAll();
        courseRepository.deleteAll();
        personRepository.deleteAll();
    }

    public Faculty createFaculty(Faculty faculty) {
        facultyRepository.save(faculty);
        return faculty;
    }

    public Student createStudent(Student student) {
        studentRepository.save(student);
        return student;
    }

    public Course createCourse(Course course) {
        courseRepository.save(course);
        return course;
    }

    public Section createSection(Section section) {
        sectionRepository.save(section);
        return section;
    }

    public Course addSectionToCourse(Section section, Course course) {
        section.setCourse(course);
        course.addSections(section);
        sectionRepository.save(section);
        courseRepository.save(course);
        return course;
    }

    public Course setAuthorForCourse(Faculty faculty, Course course) {
        course.setFaculty(faculty);
        faculty.authoredCourse(course);
        courseRepository.save(course);
        facultyRepository.save(faculty);
        return course;
    }

    public Boolean enrollStudentInSection(Student student, Section section) {
        if(section.getSeats() == 0)
            return false;
        else {
            Enrollment enrollment = new Enrollment(student, section);
            enrollment.setStudent(student);
            enrollment.setSection(section);
            student.addEnrollments(enrollment);
            section.addEnrollments(enrollment);

            studentRepository.save(student);
            enrollmentRepository.save(enrollment);
            sectionRepository.save(section);
            return true;
        }
    }


    public List<Person> findAllPersons(){
        List<Person> persons = (List<Person>)personRepository.findAll();
        return persons;
    }

    public List<Faculty> findAllFaculty(){
        List<Faculty> faculties = (List<Faculty>) facultyRepository.findAll();
        return faculties;
    }

    public List<Student> findAllStudents(){
        List<Student> students = (List<Student>)studentRepository.findAll();
        return students;
    }

    public List<Course> findAllCourses(){
        List<Course> courses = (List<Course>)courseRepository.findAll();
        return courses;
    }

    public List<Section> findAllSections(){
        List<Section> sections = (List<Section>)sectionRepository.findAll();
        return sections;
    }


    public List<Course> findCoursesForAuthor(Faculty faculty){
        List<Course> courses = faculty.getCourses();
        return courses;
    }

    public List<Section> findSectionForCourse(Course course){
        List<Section> sections = course.getSections();
        return sections;
    }

    public List<Student> findStudentsInSection(Section section){
        List<Student> students = new ArrayList<Student>();
        for(Enrollment e: section.getEnrollments()) {
            students.add(e.getStudent());
        }
        return students;
    }

    public List<Section> findSectionsForStudent(Student student){
        List <Section> sections = new ArrayList<Section>();
        for(Enrollment e: student.getEnrollments()) {
            sections.add(e.getSection());
        }
        return sections;
    }
}
