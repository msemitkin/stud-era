package ua.knu.csc.studera.domain.service;

import org.springframework.stereotype.Service;
import ua.knu.csc.studera.domain.course.Course;
import ua.knu.csc.studera.domain.course.SimpleCourse;
import ua.knu.csc.studera.domain.lecturer.SimpleLecturer;
import ua.knu.csc.studera.domain.provider.Provider;
import ua.knu.csc.studera.domain.student.SimpleStudent;
import ua.knu.csc.studera.repository.CourseJdbcRepository;
import ua.knu.csc.studera.repository.CourseRepository;
import ua.knu.csc.studera.repository.LecturerJdbcRepository;
import ua.knu.csc.studera.repository.ProviderRepository;
import ua.knu.csc.studera.repository.StudentJdbcRepository;

import java.util.List;

@Service
public class ProviderService {

    private final ProviderRepository providerRepository;
    private final CourseRepository courseRepository;
    private final LecturerJdbcRepository lecturerJdbcRepository;
    private final StudentJdbcRepository studentJdbcRepository;
    private final CourseJdbcRepository courseJdbcRepository;

    public ProviderService(
        ProviderRepository providerRepository,
        CourseRepository courseRepository,
        LecturerJdbcRepository lecturerJdbcRepository,
        StudentJdbcRepository studentJdbcRepository,
        CourseJdbcRepository courseJdbcRepository
    ) {
        this.providerRepository = providerRepository;
        this.courseRepository = courseRepository;
        this.lecturerJdbcRepository = lecturerJdbcRepository;
        this.studentJdbcRepository = studentJdbcRepository;
        this.courseJdbcRepository = courseJdbcRepository;
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
        return courseJdbcRepository.findAverageCourseCapacity(providerId);
    }

    public List<SimpleCourse> findCoursesWithNoFreePlaces(int providerId) {
        return courseJdbcRepository.findCoursesWithNoFreePlaces(providerId);
    }

    public List<SimpleLecturer> findProvidersOnWhoseCoursesGivenLecturerWorks(int lecturerId) {
        return lecturerJdbcRepository.findProvidersOnWhoseCoursesGivenLecturerWorks(lecturerId);
    }

    public List<SimpleStudent> findStudentsThatEnrolledAllCoursesOfProvider(int providerId) {
        return studentJdbcRepository.findStudentsThatEnrolledAllCoursesOfProvider(providerId);
    }

    public List<SimpleLecturer> findLecturersThatAreNotAttachedToAnyCourseOfProvider(int providerId) {
        return lecturerJdbcRepository.findLecturersThatAreNotAttachedToAnyCourseOfProvider(providerId);
    }

}
