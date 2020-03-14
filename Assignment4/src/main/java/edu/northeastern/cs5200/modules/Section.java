package edu.northeastern.cs5200.modules;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sections")
public class Section {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String title;
    private int seats;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Course course;

    @OneToMany(mappedBy="section", fetch = FetchType.EAGER)
    private List<Enrollment> Enrollments = new ArrayList<Enrollment>();

    public Section() {}

    public Section(String title, int seats) {
        super();
        this.title = title;
        this.seats = seats;
    }

    public void addEnrollments(Enrollment enrollment) {
        this.Enrollments.add(enrollment);
        if(!this.getEnrollments().contains(enrollment)) {
            this.getEnrollments().add(enrollment);
        }
    }

    public List<Enrollment> getEnrollments() {
        return Enrollments;
    }

    public void setEnrollments(List<Enrollment> Enrollments) {
        this.Enrollments = Enrollments;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
        if(!course.getSections().contains(this)) {
            course.getSections().add(this);
        }
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public int getSeats() {
        return seats;
    }
    public void setSeats(int seats) {
        this.seats = seats;
    }

}