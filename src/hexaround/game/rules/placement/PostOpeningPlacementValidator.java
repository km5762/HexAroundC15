package hexaround.game.rules.placement;

import hexaround.game.rules.ICondition;
import hexaround.game.rules.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostOpeningPlacementValidator extends Validator<PlacementContext> {
    public PostOpeningPlacementValidator() {
        super(new ArrayList<>(Arrays.asList(
                new PlacementEmpty(),
                new PlacementNextToAllyAndNotEnemy(),
                new PlacementPlayerHasCreature()
        )));
    }
}
