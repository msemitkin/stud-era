package ua.knu.csc.studera.domain.course;

import ua.knu.csc.studera.domain.provider.SimpleProvider;

public class CourseProjection implements SimpleCourse {

    private final Integer id;
    private final String name;
    private final String description;
    private final Integer studentsLimit;
    private final SimpleProvider provider;

    public CourseProjection(
        Integer id,
        String name,
        String description,
        Integer studentsLimit,
        SimpleProvider provider
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.studentsLimit = studentsLimit;
        this.provider = provider;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Integer getStudentsLimit() {
        return studentsLimit;
    }

    @Override
    public SimpleProvider getProvider() {
        return provider;
    }
}
