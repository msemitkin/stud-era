package ua.knu.csc.studera.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @Column(columnDefinition = "text")
    private String description;
    private Integer studentsLimit;
    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students;
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;
    @ManyToMany
    @JoinTable(
        name = "course_lecturer",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "lecturer_id"))
    private Set<Lecturer> lecturers;

    public Course(Integer id, String name, String description, Integer studentsLimit,
                  Set<Student> students, Provider provider, Set<Lecturer> lecturers) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.studentsLimit = studentsLimit;
        this.students = students;
        this.provider = provider;
        this.lecturers = lecturers;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStudentsLimit(Integer studentsLimit) {
        this.studentsLimit = studentsLimit;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public void setLecturers(Set<Lecturer> lecturers) {
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

    public Set<Student> getStudents() {
        return students;
    }

    public Provider getProvider() {
        return provider;
    }

    public Set<Lecturer> getLecturers() {
        return lecturers;
    }
}
