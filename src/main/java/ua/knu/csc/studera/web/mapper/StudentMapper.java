package ua.knu.csc.studera.web.mapper;

import org.springframework.stereotype.Component;
import ua.knu.csc.studera.domain.Student;
import ua.knu.csc.studera.web.dto.CreateStudentDTO;

@Component
public class StudentMapper {

    public Student toEntity(CreateStudentDTO studentDTO) {
        return new Student(null, studentDTO.getFirstName(), studentDTO.getLastName(), null);
    }
}
