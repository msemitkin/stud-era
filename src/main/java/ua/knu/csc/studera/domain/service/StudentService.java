package ua.knu.csc.studera.domain.service;

import org.springframework.stereotype.Service;
import ua.knu.csc.studera.domain.course.Course;
import ua.knu.csc.studera.domain.student.SimpleStudent;
import ua.knu.csc.studera.domain.student.Student;
import ua.knu.csc.studera.repository.CourseRepository;
import ua.knu.csc.studera.repository.StudentJdbcRepository;
import ua.knu.csc.studera.repository.StudentRepository;
import ua.knu.csc.studera.web.EntityNotFoundException;
import ua.knu.csc.studera.web.StudentsLimitExceeded;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentJdbcRepository studentJdbcRepository;
    private final CourseRepository courseRepository;

    public StudentService(
        StudentRepository studentRepository,
        StudentJdbcRepository studentJdbcRepository,
        CourseRepository courseRepository
    ) {
        this.studentRepository = studentRepository;
        this.studentJdbcRepository = studentJdbcRepository;
        this.courseRepository = courseRepository;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public List<Student> findAllByCourseId(int courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            return courseOptional.get().getStudents().stream().toList();
        } else {
            throw new EntityNotFoundException("Course with given id does not exist");
        }
    }

    public void save(Student student) {
        studentRepository.save(student);
    }

    public List<Student> getStudentsThatHaveNotEnrolledCourseYet(int courseId) {
        return studentRepository.findAll().stream()
            .filter(student -> !alreadyApplied(student, courseId))
            .collect(Collectors.toList());
    }

    private boolean alreadyApplied(Student student, Integer courseId) {
        return student.getCourses().stream()
            .map(Course::getId)
            .anyMatch(id -> id.equals(courseId));
    }

    public void addToCourse(int studentId, int courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() ->
            new EntityNotFoundException("Student with given id does not exist"));
        Course course = courseRepository.findById(courseId).orElseThrow(() ->
            new EntityNotFoundException("Course with given id does not exist"));

        if (studentRepository.studentAppliedCourse(studentId, course)) {
            throw new IllegalArgumentException("Student already applied this course");
        }
        if (course.getStudents().size() >= course.getStudentsLimit()) {
            throw new StudentsLimitExceeded("Maximum students number limit have been reached");
        }

        student.addCourse(course);
        studentRepository.save(student);

    }

    public void deleteFromCourse(int studentId, int courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() ->
            new EntityNotFoundException("Course with given id does not exist"));
        Student student = studentRepository.findById(studentId).orElseThrow(() ->
            new EntityNotFoundException("Student with given id does not exist"));
        student.removeCourse(course);
        studentRepository.save(student);
    }

    public void delete(int studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() ->
            new EntityNotFoundException("Student with given id does not exist"));
        for (Course course : List.copyOf(student.getCourses())) {
            student.removeCourse(course);
        }
        studentRepository.delete(student);
    }

    public List<SimpleStudent> findStudentsThatEnrolledAtLeastAllCoursesThatGivenStudent(int studentId) {
        return studentJdbcRepository.findStudentsThatEnrolledAtLeastAllCoursesThatGivenStudent(studentId);
    }

}
