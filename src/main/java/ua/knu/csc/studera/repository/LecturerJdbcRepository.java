package ua.knu.csc.studera.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.knu.csc.studera.domain.lecturer.LecturerProjection;
import ua.knu.csc.studera.domain.lecturer.SimpleLecturer;
import ua.knu.csc.studera.repository.util.QueryLoader;

import java.util.List;
import java.util.Map;

@Repository
public class LecturerJdbcRepository {

    public static final RowMapper<SimpleLecturer> SIMPLE_LECTURER_ROW_MAPPER = (rs, rowNum) ->
        new LecturerProjection(
            rs.getInt("id"),
            rs.getString("first_name"),
            rs.getString("last_name")
        );
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final QueryLoader queryLoader;

    public LecturerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate, QueryLoader queryLoader) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryLoader = queryLoader;
    }

    public List<SimpleLecturer> getLecturersAttachedToMoreCoursesThanGiven(
        int providerId, int courseNumber
    ) {
        String fileName = "find-lecturers-attached-to-more-provider-courses-than-x.sql";
        String query = queryLoader.getQueryAsText(fileName);
        Map<String, Integer> parameters = Map.of("id", providerId, "count", courseNumber);
        return jdbcTemplate.query(query, parameters, SIMPLE_LECTURER_ROW_MAPPER);
    }

    public List<SimpleLecturer> findLecturersThatAreNotAttachedToAnyCourseOfProvider(int providerId) {
        String fileName = "find-lecturers-that-are-not-attached-to-any-course-of-provider.sql";
        String query = queryLoader.getQueryAsText(fileName);
        return jdbcTemplate.query(query, Map.of("id", providerId), SIMPLE_LECTURER_ROW_MAPPER);
    }
}
