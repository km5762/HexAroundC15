package hexaround.game.rules;

/**
 * A condition is a lambda-like object which takes a Record containing context, and returns a ValidationResult
 * with the field "valid" describing whether the condition was successfully verified, and a message used to
 * pass information about why the validation failed.
 *
 * @param <T> the type of context the condition accepts. Context can be any type of Record.
 */
public interface ICondition<T extends Record> {
    /**
     * Used to test the condition
     *
     * @param context a Record of type T containing all the information the test needs to be verified
     * @return a ValidationResult with the "valid" field set to the validity of the condition, and
     * a message describing validation failures.
     */
    ValidationResult test(T context);
}
