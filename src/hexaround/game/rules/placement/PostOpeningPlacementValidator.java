package hexaround.game.rules.placement;

import hexaround.game.rules.ICondition;
import hexaround.game.rules.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is the validator used past the opening stage of HexAround. It initializes an instance of Validator with the
 * appropriate placement IConditions.
 */
public class PostOpeningPlacementValidator extends Validator<PlacementContext> {
    public PostOpeningPlacementValidator() {
        super(new ArrayList<>(Arrays.asList(
                new PlacementEmpty(),
                new PlacementNextToAllyAndNotEnemy(),
                new PlacementPlayerHasCreature()
        )));
    }
}
