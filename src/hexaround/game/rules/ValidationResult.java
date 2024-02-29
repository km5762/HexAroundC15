package hexaround.game.rules;

/**
 * A Record describing the result of a test or validation. The
 * "valid" field describes whether the test or validation was successful,
 * and the message field is used to pass error messages.
 *
 * @param valid a field describing whether the validation or test was successful
 * @param message a message which optionally describes errors
 */
public record ValidationResult(boolean valid, String message) {
}
