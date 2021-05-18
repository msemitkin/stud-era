package ua.knu.csc.studera.web;

public class StudentsLimitExceeded extends RuntimeException {
    public StudentsLimitExceeded(String message) {
        super(message);
    }
}
