package ua.knu.csc.studera.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String description;
    private Integer studentsLimit;
    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students;
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;
    @ManyToMany
    @JoinTable(
        name = "course_lecturer",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "lecturer_id"))
    private List<Lecturer> lecturers;

    public Course(Integer id, String name, String description, Integer studentsLimit,
                  List<Student> students, Provider provider, List<Lecturer> lecturers) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.studentsLimit = studentsLimit;
        this.students = students;
        this.provider = provider;
        this.lecturers = lecturers;
    }

    protected Course() {
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

    public Integer getStudentsLimit() {
        return studentsLimit;
    }

    public List<Student> getStudents() {
        return students;
    }
}
