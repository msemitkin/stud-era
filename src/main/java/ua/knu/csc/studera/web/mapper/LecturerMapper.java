package ua.knu.csc.studera.web.mapper;

import org.springframework.stereotype.Component;
import ua.knu.csc.studera.domain.Lecturer;
import ua.knu.csc.studera.web.dto.CreateLecturerDTO;

@Component
public class LecturerMapper {

    public Lecturer toEntity(CreateLecturerDTO lecturerDTO) {
        return new Lecturer(null, lecturerDTO.getFirstName(), lecturerDTO.getLastName(), null);
    }
}
