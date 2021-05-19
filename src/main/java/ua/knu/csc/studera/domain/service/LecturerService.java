package ua.knu.csc.studera.domain.service;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import ua.knu.csc.studera.domain.course.Course;
import ua.knu.csc.studera.domain.lecturer.Lecturer;
import ua.knu.csc.studera.domain.lecturer.LecturerProjection;
import ua.knu.csc.studera.domain.lecturer.SimpleLecturer;
import ua.knu.csc.studera.repository.CourseRepository;
import ua.knu.csc.studera.repository.LecturerRepository;
import ua.knu.csc.studera.repository.util.QueryLoader;
import ua.knu.csc.studera.web.EntityNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LecturerService {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final QueryLoader queryLoader;
    private final LecturerRepository lecturerRepository;
    private final CourseRepository courseRepository;

    public LecturerService(
        NamedParameterJdbcTemplate jdbcTemplate,
        QueryLoader queryLoader,
        LecturerRepository lecturerRepository,
        CourseRepository courseRepository
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryLoader = queryLoader;
        this.lecturerRepository = lecturerRepository;
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

    public List<SimpleLecturer> getLecturersAttachedToMoreCoursesThanGiven(
        int providerId, int courseNumber
    ) {
        String fileName = "find-lecturers-attached-to-more-provider-courses-than-x.sql";
        String query = queryLoader.getQueryAsText(fileName);
        return jdbcTemplate.query(
            query,
            Map.of("id", providerId, "count", courseNumber),
            (rs, rowNum) ->
                new LecturerProjection(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name")
                )
        );
    }


}
