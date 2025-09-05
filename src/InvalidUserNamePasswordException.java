package src;

/**
 * An InvalidUserNamePasswordException class is a custom exception whenever the
 * loginPassword for the given loginUsername is incorrect,
 * the loginUsername or loginPassword contains non-alphanumerical values,
 * the loginUsername and loginPassword is null or empty, or if the loginPassword is too short.
 *
 * @author npien-purdue
 * @version November 3, 2024
 */

public class InvalidUserNamePasswordException extends Exception implements InvalidUserNamePasswordExceptionInterface {
    public InvalidUserNamePasswordException(String message) {
        super(message);
    }
}
