package ua.knu.csc.studera.domain.service;

import org.springframework.stereotype.Service;
import ua.knu.csc.studera.domain.Course;
import ua.knu.csc.studera.domain.Provider;
import ua.knu.csc.studera.repository.CourseRepository;
import ua.knu.csc.studera.repository.ProviderRepository;

import java.util.List;

@Service
public class ProviderService {

    private final ProviderRepository providerRepository;
    private final CourseRepository courseRepository;

    public ProviderService(ProviderRepository providerRepository, CourseRepository courseRepository) {
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
}
