package hexaround.game.rules;

import java.util.List;

public class Validator<T extends Record> {
    protected List<ICondition<T>> conditions;

    public Validator (List<ICondition<T>> conditions) {
        this.conditions = conditions;
    }
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
