package ua.knu.csc.studera.domain.service;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import ua.knu.csc.studera.domain.course.Course;
import ua.knu.csc.studera.domain.course.CourseProjection;
import ua.knu.csc.studera.domain.lecturer.LecturerProjection;
import ua.knu.csc.studera.domain.lecturer.SimpleLecturer;
import ua.knu.csc.studera.domain.provider.Provider;
import ua.knu.csc.studera.domain.course.SimpleCourse;
import ua.knu.csc.studera.domain.provider.SimpleProvider;
import ua.knu.csc.studera.repository.CourseRepository;
import ua.knu.csc.studera.repository.ProviderRepository;
import ua.knu.csc.studera.repository.util.QueryLoader;

import java.util.List;
import java.util.Map;

@Service
public class ProviderService {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final QueryLoader queryLoader;
    private final ProviderRepository providerRepository;
    private final CourseRepository courseRepository;

    public ProviderService(
        NamedParameterJdbcTemplate jdbcTemplate,
        QueryLoader queryLoader,
        ProviderRepository providerRepository,
        CourseRepository courseRepository
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryLoader = queryLoader;
        this.providerRepository = providerRepository;
        this.courseRepository = courseRepository;
    }

    public List<Provider> findAll() {
        return providerRepository.findAll();
    }

    public void save(Provider provider) {
        providerRepository.save(provider);
    }

    public void addCourse(int providerId, Course course) {
        Provider provider = providerRepository.findById(providerId).orElseThrow();
        provider.addCourse(course);
        courseRepository.save(course);
    }

    public Double findAverageCourseCapacity(int providerId) {
        String query = queryLoader.getQueryAsText("average-provider-course-capacity.sql");
        return jdbcTemplate.queryForObject(query, Map.of("id", providerId), Double.class);
    }


    public List<SimpleCourse> findCoursesWithNoFreePlaces(int providerId) {
        String query = queryLoader.getQueryAsText("find-courses-with-no-free-places.sql");
        return jdbcTemplate.query(
            query,
            Map.of("id", providerId),
            (rs, rowNum) ->
                new CourseProjection(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("students_limit")
                )
        );
    }

    public List<SimpleLecturer> findProvidersOnWhoseCoursesGivenLecturerWorks(int lecturerId) {
        String fileName = "find-providers-where-given-lecturer-works.sql";
        String query = queryLoader.getQueryAsText(fileName);
        return jdbcTemplate.query(
            query,
            Map.of("id", lecturerId),
            (rs, rowNum) -> new LecturerProjection(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description")
            )
        );
    }

}
