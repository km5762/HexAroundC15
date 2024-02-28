package hexaround.game.rules;

public interface ICondition<T extends Record> {
    ValidationResult test(T context);
}
