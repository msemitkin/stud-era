package ua.knu.csc.studera.domain.course;

import ua.knu.csc.studera.domain.provider.SimpleProvider;

public interface SimpleCourse {

    Integer getId();

    String getName();

    String getDescription();

    Integer getStudentsLimit();

    SimpleProvider getProvider();
}
