package ua.knu.csc.studera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.knu.csc.studera.domain.Course;
import ua.knu.csc.studera.domain.Lecturer;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Integer> {
    @Query("""
        SELECT CASE WHEN COUNT(lecturer) > 0 THEN TRUE ELSE FALSE END 
        FROM Lecturer lecturer WHERE :course MEMBER lecturer.courses AND lecturer.id = :id
        """)
    boolean lecturerAttachedToCourse(@Param("id") Integer lecturerId, @Param("course") Course course);
}
