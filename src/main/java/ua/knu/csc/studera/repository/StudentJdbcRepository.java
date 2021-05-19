package ua.knu.csc.studera.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.knu.csc.studera.domain.student.SimpleStudent;
import ua.knu.csc.studera.domain.student.StudentProjection;
import ua.knu.csc.studera.repository.util.QueryLoader;

import java.util.List;
import java.util.Map;

@Repository
public class StudentJdbcRepository {

    public static final RowMapper<SimpleStudent> SIMPLE_STUDENT_ROW_MAPPER =
        (rs, rowNum) -> new StudentProjection(
            rs.getInt("id"),
            rs.getString("first_name"),
            rs.getString("last_name")
        );
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final QueryLoader queryLoader;

    public StudentJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate, QueryLoader queryLoader) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryLoader = queryLoader;
    }

    public List<SimpleStudent> findStudentsThatEnrolledAllCoursesOfProvider(int providerId) {
        String fileName = "find-students-that-enrolled-all-courses-of-provider.sql";
        String query = queryLoader.getQueryAsText(fileName);
        return jdbcTemplate.query(
            query,
            Map.of("id", providerId),
            SIMPLE_STUDENT_ROW_MAPPER
        );
    }

    public List<SimpleStudent> findStudentsThatEnrolledAtLeastAllCoursesThatGivenStudent(int studentId) {
        String fileName = "find-students-that-enrolled-at-least-the-same-courses-that-given-student.sql";
        String query = queryLoader.getQueryAsText(fileName);
        return jdbcTemplate.query(query, Map.of("id", studentId), SIMPLE_STUDENT_ROW_MAPPER);
    }

    public List<SimpleStudent> findStudentsThatAppliedAnyCourseFromProvider(int providerId) {
        String fileName = "students-that-applied-any-course-from-provider.sql";
        String query = queryLoader.getQueryAsText(fileName);
        return jdbcTemplate.query(query, Map.of("id", providerId), SIMPLE_STUDENT_ROW_MAPPER);
    }
}
