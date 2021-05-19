package ua.knu.csc.studera.domain.lecturer;

public class LecturerProjection implements SimpleLecturer {

    private final Integer id;
    private final String firstName;
    private final String lastName;

    public LecturerProjection(
        Integer id,
        String firstName,
        String lastName
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }
}
