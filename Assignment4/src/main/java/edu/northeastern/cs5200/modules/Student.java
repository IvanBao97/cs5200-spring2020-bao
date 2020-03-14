package edu.northeastern.cs5200.modules;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Student extends Person{
    private int gradYear;
    private long scholarship;

    @OneToMany(mappedBy="student", fetch = FetchType.EAGER)
    private Set<Enrollment> Enrollments = new HashSet<Enrollment>();

    public void addEnrollments(Enrollment e) {
        this.Enrollments.add(e);
        if(!this.getEnrollments().contains(e)) {
            this.getEnrollments().add(e);
        }
    }

    public Student() {}

    public Student(String firstName, String lastName, String username, String password, int gradYear, long scholarship) {
        super.setFirstName(firstName);
        super.setLastName(lastName);
        super.setUsername(username);
        super.setPassword(password);
        this.gradYear = gradYear;
        this.scholarship = scholarship;
    }

    public int getGradYear() {
        return gradYear;
    }
    public void setGradYear(int gradYear) {
        this.gradYear = gradYear;
    }
    public long getScholarship() {
        return scholarship;
    }
    public void setScholarship(long scholarship) {
        this.scholarship = scholarship;
    }

    public Set<Enrollment> getEnrollments() {
        return Enrollments;
    }

    public void setEnrollments(Set<Enrollment> Enrollments) {
        this.Enrollments = Enrollments;
    }
}