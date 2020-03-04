package ro.fortech.internship.vinylshop.common.exception;

public class InvalidPasswordOrEmailException extends RuntimeException {
    public InvalidPasswordOrEmailException(String message) {
        super(message);
    }
}
