package ua.knu.csc.studera.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.knu.csc.studera.domain.course.CourseProjection;
import ua.knu.csc.studera.domain.course.SimpleCourse;
import ua.knu.csc.studera.domain.provider.ProviderProjection;
import ua.knu.csc.studera.repository.util.QueryLoader;

import java.util.List;
import java.util.Map;

@Repository
public class CourseJdbcRepository {

    public static final RowMapper<SimpleCourse> SIMPLE_COURSE_ROW_MAPPER = (rs, rowNum) ->
        new CourseProjection(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getInt("students_limit"),
            new ProviderProjection(
                rs.getInt("provider_id"),
                rs.getString("provider_name"),
                rs.getString("provider_description")
            )
        );
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final QueryLoader queryLoader;

    public CourseJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate, QueryLoader queryLoader) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryLoader = queryLoader;
    }

    public List<SimpleCourse> findCoursesWithNoFreePlaces(int providerId) {
        String query = queryLoader.getQueryAsText("find-courses-with-no-free-places.sql");
        return jdbcTemplate.query(query, Map.of("id", providerId), SIMPLE_COURSE_ROW_MAPPER);
    }

    public Double findAverageCourseCapacity(int providerId) {
        String query = queryLoader.getQueryAsText("average-provider-course-capacity.sql");
        return jdbcTemplate.queryForObject(query, Map.of("id", providerId), Double.class);
    }
}
