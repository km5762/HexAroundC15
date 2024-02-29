package hexaround.game.rules;

import java.util.List;

public class Validator<T extends Record> {
    protected List<ICondition<T>> conditions;

    /**
     * Used to construct a Validator. A Validator takes in a list of IConditions that accept the same type of Context (T),
     * and is used to check all of these conditions sequentially
     * @param conditions a List of IConditions which take the same type of context
     */
    public Validator (List<ICondition<T>> conditions) {
        this.conditions = conditions;
    }

    /**
     * Used to determine whether all conditions hold for a given context
     * @param context a Record containing the context for the situation being validated
     * @return a ValidationResult describing the outcome of the validation. If any of the
     * condition's tests failed, the "valid" field will be set to false, and a message will
     * describe the issue. Otherwise, the "valid" field will be set to true and message will
     * be null.
     */
    public ValidationResult validate(T context) {
        for (ICondition<T> condition : conditions) {
            ValidationResult result = condition.test(context);
            if (!result.valid()) {
                return result;
            }
        }
        return new ValidationResult(true, null);
    }
}
