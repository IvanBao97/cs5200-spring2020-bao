package edu.northeastern.cs5200;

import edu.northeastern.cs5200.daos.UniversityDao;
import edu.northeastern.cs5200.modules.*;
import edu.northeastern.cs5200.repositories.*;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUniversityDao {

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
    @Autowired
    UniversityDao udao;


    private static boolean initialized = false;

    @Before
    public void A_testDelete() {
        if (!initialized) {
            udao.truncateDatabase();
            initialized = true;
        }
    }


    @Test
    public void B_testCreateFaculty() {
        Faculty Alan = new Faculty( "Alan", "Turin", "alan", "password", "123A", true);
        Faculty Ada = new Faculty( "Ada", "Lovelace", "ada", "password", "123B", true);
        udao.createFaculty(Alan);
        udao.createFaculty(Ada);
    }



    @Test
    public void C_testCreateStudent() {
        Student Alice = new Student("Alice", "Wonderland", "alice", "password", 2020, 12000);
        Student Bob = new Student("Bob", "Hope", "bob", "password", 2021, 23000);
        Student Charlie = new Student("Charlie", "Brown", "charlie", "password", 2019, 21000);
        Student Dan = new Student("Dan", "Craig", "dan", "password", 2019, 0);
        Student Edward = new Student("Edward", "Scissorhands", "edward", "password", 2022, 11000);
        Student Frank = new Student("Frank", "Herbert", "frank", "password", 2018, 0);
        Student Gregory = new Student("Gregory", "Peck", "edward", "password", 2023, 10000);
        udao.createStudent(Alice);
        udao.createStudent(Bob);
        udao.createStudent(Charlie);
        udao.createStudent(Dan);
        udao.createStudent(Edward);
        udao.createStudent(Frank);
        udao.createStudent(Gregory);
    }


    @Test
    public void D_testCreateCourse() {

        Course CS1234 = new Course("CS1234");
        Course CS2345 = new Course("CS2345");
        Course CS3456 = new Course("CS3456");
        Course CS4567 = new Course("CS4567");

        udao.createCourse(CS1234);
        udao.createCourse(CS2345);
        udao.createCourse(CS3456);
        udao.createCourse(CS4567);

        udao.setAuthorForCourse(facultyRepository.findByName("Alan"), courseRepository.findByLabel("CS1234"));
        udao.setAuthorForCourse(facultyRepository.findByName("Alan"), courseRepository.findByLabel("CS2345"));
        udao.setAuthorForCourse(facultyRepository.findByName("Ada"), courseRepository.findByLabel("CS3456"));
        udao.setAuthorForCourse(facultyRepository.findByName("Ada"), courseRepository.findByLabel("CS4567"));
    }


    @Test
    public void E_testCreateSection() {

        Section SEC4321 = new Section("SEC4321", 50);
        Section SEC5432 = new Section("SEC5432", 50);
        Section SEC6543 = new Section("SEC6543", 50);
        Section SEC7654 = new Section("SEC7654", 50);

        udao.createSection(SEC4321);
        udao.createSection(SEC5432);
        udao.createSection(SEC6543);
        udao.createSection(SEC7654);

        udao.addSectionToCourse(sectionRepository.findByTitle("SEC4321"), courseRepository.findByLabel("CS1234"));
        udao.addSectionToCourse(sectionRepository.findByTitle("SEC5432"), courseRepository.findByLabel("CS1234"));
        udao.addSectionToCourse(sectionRepository.findByTitle("SEC6543"), courseRepository.findByLabel("CS2345"));
        udao.addSectionToCourse(sectionRepository.findByTitle("SEC7654"), courseRepository.findByLabel("CS2345"));

    }


    @Test
    public void F_testAddEnrollmentRelation() {

        udao.enrollStudentInSection(studentRepository.findByName("Alice"), sectionRepository.findByTitle("SEC4321"));
        udao.enrollStudentInSection(studentRepository.findByName("Alice"), sectionRepository.findByTitle("SEC5432"));
        udao.enrollStudentInSection(studentRepository.findByName("Bob"), sectionRepository.findByTitle("SEC5432"));
        udao.enrollStudentInSection(studentRepository.findByName("Charlie"), sectionRepository.findByTitle("SEC6543"));

    }

}
