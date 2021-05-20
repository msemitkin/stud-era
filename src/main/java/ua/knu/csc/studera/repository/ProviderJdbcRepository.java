package ua.knu.csc.studera.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.knu.csc.studera.domain.lecturer.SimpleLecturer;
import ua.knu.csc.studera.domain.provider.ProviderProjection;
import ua.knu.csc.studera.domain.provider.SimpleProvider;
import ua.knu.csc.studera.repository.util.QueryLoader;

import java.util.List;
import java.util.Map;

@Repository
public class ProviderJdbcRepository {
    private static final RowMapper<SimpleProvider> SIMPLE_PROVIDER_ROW_MAPPER = (rs, rowNum) ->
        new ProviderProjection(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("description")
        );

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final QueryLoader queryLoader;

    public ProviderJdbcRepository(
        NamedParameterJdbcTemplate jdbcTemplate,
        QueryLoader queryLoader
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryLoader = queryLoader;
    }

    public List<SimpleProvider> findProvidersOnWhoseCoursesGivenLecturerWorks(int lecturerId) {
        String fileName = "find-providers-where-given-lecturer-works.sql";
        String query = queryLoader.getQueryAsText(fileName);
        return jdbcTemplate.query(query, Map.of("id", lecturerId), SIMPLE_PROVIDER_ROW_MAPPER);
    }
}
