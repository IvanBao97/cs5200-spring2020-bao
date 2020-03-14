package edu.northeastern.cs5200.modules;

import javax.persistence.*;

@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private int grade;
    private String feedback;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Student student;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Section section;

    public Enrollment() {}

    public Enrollment( Student student, Section section) {
        this.student = student;
        this.section = section;

        // decrement seat in section
        this.section.setSeats(this.section.getSeats()-1);
    }

    public void setStudent(Student student) {
        this.student = student;
        if (!student.getEnrollments().contains(this)) {
            student.getEnrollments().add(this);
        }
    }

    public void setSection(Section section) {
        this.section = section;
        if (!section.getEnrollments().contains(this)) {
            section.getEnrollments().add(this);
        }
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getGrade() {
        return grade;
    }
    public void setGrade(int grade) {
        this.grade = grade;
    }
    public String getFeedback() {
        return feedback;
    }
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Student getStudent() {
        return student;
    }

    public Section getSection() {
        return section;
    }
}

