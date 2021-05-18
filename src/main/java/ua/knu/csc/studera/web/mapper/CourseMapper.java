package ua.knu.csc.studera.web.mapper;

import org.springframework.stereotype.Component;
import ua.knu.csc.studera.domain.Course;
import ua.knu.csc.studera.web.dto.CreateCourseDTO;

@Component
public class CourseMapper {

    public Course toEntity(CreateCourseDTO createCourseDTO) {
        return new Course(null, createCourseDTO.getName(), createCourseDTO.getDescription(),
            createCourseDTO.getStudentsLimit(), null, null, null);
    }
}
