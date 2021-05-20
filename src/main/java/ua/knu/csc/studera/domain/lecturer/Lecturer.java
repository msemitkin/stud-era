package ua.knu.csc.studera.domain.lecturer;

import ua.knu.csc.studera.domain.course.Course;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "lecturer")
public class Lecturer implements SimpleLecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @ManyToMany
    private Set<Course> courses;

    public Lecturer(Integer id, String firstName, String lastName, Set<Course> courses) {
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

    public Set<Course> getCourses() {
        return courses;
    }
}
