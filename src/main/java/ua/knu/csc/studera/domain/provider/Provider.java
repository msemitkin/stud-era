package ua.knu.csc.studera.domain.provider;

import ua.knu.csc.studera.domain.course.Course;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "provider")
public class Provider implements SimpleProvider {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    @Column(columnDefinition = "text")
    private String description;
    @OneToMany(mappedBy = "provider")
    private Set<Course> courses;

    public Provider(Integer id, String name, String description, Set<Course> courses) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.courses = courses;
    }

    protected Provider() {
    }

    public void addCourse(Course course) {
        this.getCourses().add(course);
        course.setProvider(this);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<Course> getCourses() {
        return courses;
    }
}
