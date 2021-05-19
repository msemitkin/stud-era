package ua.knu.csc.studera.domain.course;

public class CourseProjection implements SimpleCourse {

    private final Integer id;
    private final String name;
    private final String description;
    private final Integer studentsLimit;

    public CourseProjection(
        Integer id,
        String name,
        String description,
        Integer studentsLimit
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.studentsLimit = studentsLimit;
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
}
