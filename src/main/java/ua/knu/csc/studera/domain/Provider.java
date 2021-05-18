package ua.knu.csc.studera.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "provider")
public class Provider {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    @Column(columnDefinition = "text")
    private String description;
    @OneToMany(mappedBy = "provider")
    private List<Course> courses;

    public Provider(Integer id, String name, String description, List<Course> courses) {
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

    public List<Course> getCourses() {
        return courses;
    }
}
