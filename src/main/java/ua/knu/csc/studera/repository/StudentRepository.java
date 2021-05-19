package ua.knu.csc.studera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.knu.csc.studera.domain.course.Course;
import ua.knu.csc.studera.domain.student.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query("""
        SELECT CASE WHEN COUNT(student) > 0 THEN TRUE ELSE FALSE END 
        FROM Student student WHERE :course MEMBER student.courses AND student.id = :id
        """)
    boolean studentAppliedCourse(@Param("id") Integer studentId, @Param("course") Course course);
}
