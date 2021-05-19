package ua.knu.csc.studera.domain.service;

import org.springframework.stereotype.Service;
import ua.knu.csc.studera.domain.course.Course;
import ua.knu.csc.studera.domain.lecturer.Lecturer;
import ua.knu.csc.studera.domain.lecturer.SimpleLecturer;
import ua.knu.csc.studera.repository.CourseRepository;
import ua.knu.csc.studera.repository.LecturerJdbcRepository;
import ua.knu.csc.studera.repository.LecturerRepository;
import ua.knu.csc.studera.web.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LecturerService {

    private final LecturerRepository lecturerRepository;
    private final LecturerJdbcRepository lecturerJdbcRepository;
    private final CourseRepository courseRepository;

    public LecturerService(
        LecturerRepository lecturerRepository,
        LecturerJdbcRepository lecturerJdbcRepository,
        CourseRepository courseRepository
    ) {
        this.lecturerRepository = lecturerRepository;
        this.lecturerJdbcRepository = lecturerJdbcRepository;
        this.courseRepository = courseRepository;
    }

    public List<Lecturer> findAll() {
        return lecturerRepository.findAll();
    }

    public List<Lecturer> findAllByCourse(int courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        Course course = courseOptional.orElseThrow(() ->
            new EntityNotFoundException("Course with given id does not exist"));
        return course.getLecturers().stream().toList();
    }

    public void save(Lecturer lecturer) {
        lecturerRepository.save(lecturer);
    }

    public List<Lecturer> findAllNotAttachedToCourse(int courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new EntityNotFoundException("Course with given id does not exist");
        }
        return lecturerRepository.findAll().stream()
            .filter(lecturer -> !isAttachedToCourse(lecturer, courseId))
            .collect(Collectors.toList());
    }

    private boolean isAttachedToCourse(Lecturer lecturer, Integer courseId) {
        return lecturer.getCourses().stream()
            .map(Course::getId)
            .anyMatch(id -> id.equals(courseId));
    }

    public void addToCourse(int lecturerId, int courseId) {
        Lecturer lecturer = lecturerRepository.findById(lecturerId).orElseThrow(() ->
            new EntityNotFoundException("Lecturer with given id does not exist"));
        Course course = courseRepository.findById(courseId).orElseThrow(() ->
            new EntityNotFoundException("Course with given id does not exist"));
        if (lecturerRepository.lecturerAttachedToCourse(lecturerId, course)) {
            throw new IllegalArgumentException("Lecturer is already attached to this course");
        }
        lecturer.addCourse(course);
        lecturerRepository.save(lecturer);
    }

    public void deleteFromCourse(int lecturerId, int courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() ->
            new EntityNotFoundException("Course with given id does not exist"));
        Lecturer lecturer = lecturerRepository.findById(lecturerId).orElseThrow(() ->
            new EntityNotFoundException("Lecturer with given id does not exist"));
        lecturer.removeCourse(course);
        lecturerRepository.save(lecturer);
    }

    public void delete(int lecturerId) {
        Lecturer lecturer = lecturerRepository.findById(lecturerId).orElseThrow(() ->
            new EntityNotFoundException("Lecturer with given id does not exist"));
        for (Course course : lecturer.getCourses()) {
            lecturer.removeCourse(course);
        }
        lecturerRepository.delete(lecturer);
    }

    public List<SimpleLecturer> getLecturersAttachedToMoreCoursesThanGiven(int providerId, int courseNumber) {
        return lecturerJdbcRepository.getLecturersAttachedToMoreCoursesThanGiven(providerId, courseNumber);
    }

}
