package ua.knu.csc.studera.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "lecturer")
public class Lecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String firstName;
    private String lastName;
    @ManyToMany
    private List<Course> courses;

    public Lecturer(Integer id, String firstName, String lastName, List<Course> courses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.courses = courses;
    }

    protected Lecturer() {
    }

    public void addCourse(Course course) {
        this.courses.add(course);
        course.getLecturers().add(this);
    }

    public void removeCourse(Course course) {
        this.courses.remove(course);
        course.getLecturers().remove(this);
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
