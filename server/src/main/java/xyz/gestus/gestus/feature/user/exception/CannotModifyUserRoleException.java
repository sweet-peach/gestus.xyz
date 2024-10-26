package xyz.gestus.gestus.feature.user.exception;

public class CannotModifyUserRoleException extends RuntimeException{
    public CannotModifyUserRoleException(String message) {
        super(message);
    }
}
