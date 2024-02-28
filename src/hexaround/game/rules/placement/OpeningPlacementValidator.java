package hexaround.game.rules.placement;

import hexaround.game.rules.ICondition;
import hexaround.game.rules.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OpeningPlacementValidator extends Validator<PlacementContext> {
    public OpeningPlacementValidator() {
        super(new ArrayList<>(Arrays.asList(
                new PlacementEmpty(),
                new PlacementConnected(),
                new PlacementPlayerHasCreature()
        )));
    }
}
