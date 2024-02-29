package hexaround.game.rules.placement;

import hexaround.game.rules.ICondition;
import hexaround.game.rules.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This is the validator used in the opening stages of HexAround. It initializes an instance of Validator with the
 * appropriate placement IConditions.
 */
public class OpeningPlacementValidator extends Validator<PlacementContext> {
    public OpeningPlacementValidator() {
        super(new ArrayList<>(Arrays.asList(
                new PlacementEmpty(),
                new PlacementConnected(),
                new PlacementPlayerHasCreature()
        )));
    }
}
