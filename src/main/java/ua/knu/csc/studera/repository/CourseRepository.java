package ua.knu.csc.studera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.knu.csc.studera.domain.course.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
}
