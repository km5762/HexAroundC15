# TDD Tests and refactorings

| ID | Done | Description                                                                                                                                         |
|:--:|:----:|:----------------------------------------------------------------------------------------------------------------------------------------------------|
| T  |  X   | Return instance of ICreature on call to CreatureFactory.make()                                                                                      | 
| R  |  X   | Add utility function to load configs for factory testing                                                                                            | 
| T  |  X   | Return instance of ICreature with correct name on call to CreatureFactory.make()                                                                    | 
| R  |  X   | Refactor Creature constructor to take existing CreatureDefinition record                                                                            |
| T  |  X   | Return instance of ICreature with correct maxDistance on call to CreatureFactory.make()                                                             |
| R  |  X   | Change creature factory to use map instead of list for lookup - intention is that each CreatureName only maps to one definition                     |
| T  |  X   | Return instance of ICreature with correct properties on call to CreatureFactory.make()                                                              | 
| R  |  X   | Move non trivial builder setup into helper functions                                                                                                | 
| T  |  X   | isOccupied when location is not occupied                                                                                                            | 
| T  |  X   | isOccupied when location is occupied                                                                                                                |
| T  |  X   | getCreatureAt when board space is not occupied                                                                                                      | 
| T  |  X   | getCreatureAt when board space is occupied                                                                                                          |
| T  |  X   | hasProperty when creature does not have property                                                                                                    | 
| T  |  X   | hasProperty when creature has property                                                                                                              | 
| T  |  X   | canReach when creature cannot reach                                                                                                                 | 
| R  |  X   | Change variable maxRange -> maxDistance to be more consistent with CreatureDefinition                                                               |
| T  |  X   | canReach when creature can reach                                                                                                                    | 
| R  |  X   | Move basic functionality for getting/putting ICreatures on board into seperate Board class                                                          |
| T  |  X   | board.getAllCreatures when no creatures at point                                                                                                    | 
| T  |  X   | board.getAllCreatures when creatures at point                                                                                                       |
| T  |  X   | board.getTopCreature when there are no creatures at point                                                                                           |
| T  |  X   | board.getTopCreature when there are creatures at point                                                                                              |
| R  |  X   | Use @BeforeEach directive to make testing more concise                                                                                              | 
| T  |  X   | board.placeCreature when no creatures at point                                                                                                      |
| T  |  X   | board.placeCreature when already creatures at point                                                                                                 |
| T  |  X   | board.removeCreature when no creatures at point                                                                                                     |
| T  |  X   | board.removeCreature                                                                                                                                |
| T  |  X   | board.removeCreature and removing the creature leaves the point empty                                                                               |
| T  |  X   | board.removeAllCreatures                                                                                                                            | 
| T  |  X   | board.moveCreature from an empty point                                                                                                              |
| T  |  X   | board.moveCreature when no creature with name at point                                                                                              |
| R  |  X   | Move calculateDistance helper from GameManager to Board                                                                                             |
| T  |  X   | board.moveIsDisconnecting when move is not disconnecting                                                                                            |
| T  |  X   | board.moveIsDisconnecting when creature being moved is the only one on board                                                                        |
| R  |  X   | Move getting total creatures operation to private helper                                                                                            |
| T  |  X   | board.moveIsDisconnecting when move is disconnecting                                                                                                |
| R  |  X   | Create UnitVector and Vectors classes to allow for easier testing/more semantic access to cardinal directions                                       |
| T  |  X   | board.placementIsDisconnecting when placement is not disconnecting                                                                                  |
| T  |  X   | board.placementIsDisconnecting when placement is the first in the game                                                                              |
| T  |  X   | board.placementISDisconnecting when placement is disconnecting                                                                                      |
| T  |  X   | board.isConnected when board is empty                                                                                                               |
| R  |  X   | Move both checks for the guard clause into one or condition at the start of isConnected                                                             |
| T  |  X   | board.isConnected when board is connected                                                                                                           |
| T  |  X   | board.isConnected when board is disconnected                                                                                                        |
| T  |  X   | board.clone properly returns deep copy                                                                                                              |
| R  |  X   | Prefer inbuilt Java getOrDefault as opposed to containsKey checking for Board.getAllCreatures()                                                     |
| T  |  X   | GameManager.placeCreature returns legal MoveResponse                                                                                                |
| R  |  X   | Prefer Java Optionals vs. null returns to force consumers to deal with null case                                                                    |
| T  |  X   | CreatureFactory.makeCreature when creature name is not recognized in creature definitions                                                           | 
| T  |  X   | GameManager.moveCreature returns legal MoveResponse when moving on empty board                                                                      |
| R  |  X   | Make calculateDistance return type double -> int since distances will always be integers                                                            |
| T  |  X   | GameManager.moveCreature returns legal MoveResponse when moving in a connected manner                                                               |
| T  |  X   | GameManager.moveCreature returns legal MoveResponse when moving results in 1 creature stack                                                         |
| T  |  X   | GameManager.moveCreature returns move error on disconnecting moves                                                                                  |
| T  |  X   | GameManager.moveCreature does not affect board state on a disconnecting move                                                                        |
| T  |  X   | GameManager.moveCreature returns move error on move that splits colony                                                                              |
| R  |  X   | Move functionality for calculating distances/getting neighbors into HexPoint class                                                                  | 
| T  |  X   | getNeighboringPoints of hex point                                                                                                                   | 
| T  |  X   | calculateDistanceTo own hex point                                                                                                                   | 
| R  |  X   | Extract interface from HexPoint to provide a generalization for all types of points                                                                 |
| T  |  X   | calculateDistanceTo another hex point                                                                                                               | 
| T  |  X   | clone HexPoint                                                                                                                                      | 
| R  |  X   | Move logic for interacting with creatures at a certain point from List<ICreature> -> CreatureStack class                                            | 
| T  |  X   | addCreature to CreatureStack                                                                                                                        | 
| T  |  X   | removeCreature from CreatureStack                                                                                                                   | 
| T  |  X   | getTopCreature from empty CreatureStack                                                                                                             | 
| T  |  X   | getTopCreature from CreatureStack                                                                                                                   | 
| T  |  X   | getCreatureWithNameAndOwner from creatureStack when no creature with name match                                                                     | 
| T  |  X   | getCreatureWithNameAndOwner from creatureStack when no creature with owner match                                                                    | 
| T  |  X   | getCreatureWithNameAndOwner from creatureStack                                                                                                      |
| T  |  X   | equals override when object is same instance of CreatureStack                                                                                       | 
| T  |  X   | equals override when object is not an instance of CreatureStack                                                                                     | 
| T  |  X   | equals override when object is a deep copy of CreatureStack                                                                                         | 
| T  |  X   | clone returns a deep copy of CreatureStack                                                                                                          | 
| R  |  X   | Builder now returns reduced interface (IHexAroundGameManager) instead of extended interface (IHexAround1). Tests for IHexAround1 have been removed. |
| R  |  X   | Move all vector/point related classes into geometry subpackage in board                                                                             | 
| T  |  X   | Return move error when specified creature doesnt exist                                                                                              | 
| R  |  X   | Move all MoveResponses into seperate static class                                                                                                   | 
| T  |  X   | Return move error when placing a creature that does has not been defined                                                                            | 
| R  |  X   | Move point checks into seperate IPointCondition lambda-like objects                                                                                 |
| T  |  X   | PointConnected.test when moving on empty board                                                                                                      |
| R  |  X   | Move board simulation operations in connectidness test to private helper                                                                            | 
| R  |  X   | Rename IPointCondition -> IMoveCondition since we are really evaluating points in the context of a movement                                         | 
| T  |  X   | MoveConnected.test when moving on a non empty board                                                                                                 |
| T  |  X   | MoveConnected.test when moving generates 1 stack on the board                                                                                       |
| T  |  X   | MoveConnected.test when moving to a disconnected point                                                                                              |
| T  |  X   | MoveConnected.test when moving splits the colony in two                                                                                             |
| R  |  X   | Extract move connection testing into private helper                                                                                                 |
| T  |  X   | MoveEmpty.test when neighboring tile is not empty                                                                                                   |
| T  |  X   | MoveEmpty.test when neighboring tile is empty                                                                                                       |
| T  |  X   | MoveSlideable.test when moving on an empty board                                                                                                    |
| T  |  X   | MoveSlideable.test when moving to a tile with 1 adjacent piece                                                                                      |
| T  |  X   | MoveSlideable.test when moving to a tile with 2 adjacent pieces                                                                                     |
| R  |  X   | Extract move slideability testing into private helper                                                                                               | 
| R  |  X   | Move non obvious boolean checks into named variables (commonNeighbor)                                                                               | 
| T  |  X   | MoveInline.test when moving a tile with the same X coordinate                                                                                       |
| T  |  X   | MoveInline.test when moving a tile with the same Y coordinate                                                                                       |
| T  |  X   | MoveInline.test when moving a tile on the same diagonal                                                                                             |
| T  |  X   | PathAtRange.test when path is equal to the creatures max range                                                                                      |
| T  |  X   | PathAtRange.test when path is not equal to creatures max range (less than)                                                                          |
| T  |  X   | PathAtRange.test when path is not equal to creatures max range (greater than)                                                                       |
| R  |  X   | Add helper methods to CreatureFactory when setting MovementRules to clearly denote categories                                                       | 
| T  |  X   | PathDestinationEmpty.test when path destination is empty                                                                                            |
| T  |  X   | PathDestinationEmpty.test when path destination is not empty                                                                                        | 
| T  |  X   | PathDestinationConnected.test when path destination is connected                                                                                    | 
| T  |  X   | PathDestinationConnected.test when path destination is disconnected                                                                                 | 
| T  |  X   | PathDestinationConnected.test when moving to path destination splits the colony                                                                     | 
| T  |  X   | PathUpToDestinationEmpty.test when the path is entirely empty                                                                                       | 
| T  |  X   | PathUpToDestinationEmpty.test when only the destination is occupied                                                                                 | 
| T  |  X   | PathUpToDestinationEmpty.test when the path contains occupied points                                                                                | 
| R  |  X   | Make all types of conditions implement the same generic interface with a paramaterized type rather than one for each type of condition              |
| R  |  X   | Pass validators as named MovementRules record instead of individually                                                                               | 
| T  |  X   | Board.existsPath for walking creature                                                                                                               | 
| T  |  X   | Board.existsPath when walking path out of range                                                                                                     | 
| T  |  X   | Board.existsPath when walking path destination occupied                                                                                             | 
| T  |  X   | Board.existsPath when walking path blocked                                                                                                          | 
| T  |  X   | Board.existsPath when walking path destination not connected                                                                                        | 
| T  |  X   | Board.existsPath when walking creature pinned (has no moves that dont split colony)                                                                 | 
| T  |  X   | Board.existsPath when walking creature surrounded                                                                                                   | 
| T  |  X   | Board.existsPath for walking intruding creature                                                                                                     | 
| T  |  X   | Board.existsPath when walking intruding path out of range                                                                                           | 
| T  |  X   | Board.existsPath when walking intruding path destination not connected                                                                              | 
| T  |  X   | Board.existsPath when walking intruding creature pinned                                                                                             | 
| T  |  X   | Board.existsPath for walking creature with effect                                                                                                   | 
| T  |  X   | Board.existsPath when walking effect path out of range                                                                                              | 
| T  |  X   | Board.existsPath when walking effect path blocked                                                                                                   | 
| T  |  X   | Board.existsPath when walking effect path destination not connected                                                                                 | 
| T  |  X   | Board.existsPath when walking effect creature pinned                                                                                                | 
| T  |  X   | Board.existsPath when walking effect creature surrounded                                                                                            | 
| T  |  X   | Board.existsPath for running creature                                                                                                               | 
| T  |  X   | Board.existsPath when running path out of range                                                                                                     | 
| T  |  X   | Board.existsPath when running path destination occupied                                                                                             | 
| T  |  X   | Board.existsPath when running path too small                                                                                                        | 
| T  |  X   | Board.existsPath when running path blocked                                                                                                          | 
| T  |  X   | Board.existsPath when running path destination not connected                                                                                        | 
| T  |  X   | Board.existsPath when running creature pinned                                                                                                       | 
| T  |  X   | Board.existsPath when running creature surrounded                                                                                                   | 
| T  |  X   | Board.existsPath when running creature has no moves long enough                                                                                     | 
| T  |  X   | Board.existsPath for running intruding creature                                                                                                     | 
| T  |  X   | Board.existsPath when running intruding path out of range                                                                                           | 
| T  |  X   | Board.existsPath when running intruding path destination not connected                                                                              | 
| T  |  X   | Board.existsPath when running intruding creature has no moves long enough                                                                           | 
| T  |  X   | Board.existsPath when running intruding creature is pinned                                                                                          | 
| T  |  X   | Board.existsPath for running effect creature                                                                                                        | 
| T  |  X   | Board.existsPath when running effect path out of range                                                                                              | 
| T  |  X   | Board.existsPath when running effect path destination not connected                                                                                 | 
| T  |  X   | Board.existsPath when running effect path too small                                                                                                 | 
| T  |  X   | Board.existsPath when running creature pinned                                                                                                       | 
| T  |  X   | Board.existsPath for flying creature                                                                                                                | 
| T  |  X   | Board.existsPath when flying path out of range                                                                                                      | 
| T  |  X   | Board.existsPath when flying path destination occupied                                                                                              | 
| T  |  X   | Board.existsPath when flying path destination not connected                                                                                         | 
| T  |  X   | Board.existsPath when flying creature pinned                                                                                                        | 
| T  |  X   | Board.existsPath for flying intruding creature                                                                                                      |
| T  |  X   | Board.existsPath when flying intruding path out of range                                                                                            |
| T  |  X   | Board.existsPath when flying intruding path destination not connected                                                                               |
| T  |  X   | Board.existsPath when flying intruding creature pinned                                                                                              |
| T  |  X   | Board.existsPath for flying effect creature                                                                                                         |
| T  |  X   | Board.existsPath when flying effect path out of range                                                                                               |
| T  |  X   | Board.existsPath when flying effect path destination not connected                                                                                  |
| T  |  X   | Board.existsPath when flying effect creature pinned                                                                                                 |
| T  |  X   | Board.existsPath for jumping creature                                                                                                               |
| T  |  X   | Board.existsPath when jumping path out of range                                                                                                     |
| T  |  X   | Board.existsPath when jumping path destination occupied                                                                                             |
| T  |  X   | Board.existsPath when jumping path destination not connected                                                                                        |
| T  |  X   | Board.existsPath when jumping path destination not inline                                                                                           |
| T  |  X   | Board.existsPath when jumping creature pinned                                                                                                       |
| T  |  X   | Board.existsPath for jumping intruding creature                                                                                                     |
| T  |  X   | Board.existsPath when jumping intruding path out of range                                                                                           |
| T  |  X   | Board.existsPath when jumping intruding path destination not connected                                                                              |
| T  |  X   | Board.existsPath when jumping intruding path destination not inline                                                                                 |
| T  |  X   | Board.existsPath when jumping intruding creature pinned                                                                                             |
| T  |  X   | Board.existsPath for jumping effect creature                                                                                                        |
| T  |  X   | Board.existsPath when jumping effect path out of range                                                                                              |
| T  |  X   | Board.existsPath when jumping effect path destination not connected                                                                                 |
| T  |  X   | Board.existsPath when jumping effect path destination not inline                                                                                    |
| T  |  X   | Board.existsPath when jumping effect creature pinned                                                                                                |
| T  |  X   | Board.existsPath when creature trapped                                                                                                              |
| T  |  X   | Board.existsPath when flying creature surrounded                                                                                                    |
| R  |  X   | Move pre movement logic into PreMoveConditions for each creature                                                                                    |
| T  |  X   | PreMovePinned.test when not pinned                                                                                                                  |
| T  |  X   | PreMovePinned.test when pinned                                                                                                                      |
| T  |  X   | PreMoveSurrounded.test when not surrounded                                                                                                          |
| T  |  X   | PreMoveSurrounded.test when surrounded                                                                                                              |
| T  |  X   | PreMoveTrapped.test when not trapped                                                                                                                |
| T  |  X   | PreMoveTrapped.test when above trapper                                                                                                              |
| T  |  X   | PreMoveTrapped.test when trapped                                                                                                                    |
| T  |  X   | Board.existsPath when creature trapped                                                                                                              |
| T  |  X   | Board.existsPath when flying creature surrounded                                                                                                    |
| T  |  X   | PathDestinationRemovable.test when removing path destination does not disconnect colony                                                             | 
| T  |  X   | PathDestinationRemovable.test when removing path destination does disconnect colony                                                                 |
| T  |  X   | Board.existsPath when kamikaze path destination is pinned                                                                                           |
| T  |  X   | Board.existsPath when intruding path would result in a stack size > 2                                                                               |
| T  |  X   | PlacementEmpty.test when placement empty                                                                                                            |
| T  |  X   | PlacementEmpty.test when placement not empty                                                                                                        |
| T  |  X   | PlacementNextToAllyAndNotEnemy.test when placement not next to any creatures                                                                        |
| T  |  X   | PlacementNextToAllyAndNotEnemy.test when placement next to ally                                                                                     |
| T  |  X   | PlacementNextToAllyAndNotEnemy.test when placement next to enemy                                                                                    |
| T  |  X   | PlacementNextToAllyAndNotEnemy.test when placement next to enemy and ally                                                                           |
| T  |  X   | Player.decrementCreatures                                                                                                                           |
| T  |  X   | Player.incrementCreatures                                                                                                                           |
| T  |  X   | Player.outOfCreatures when out of creatures                                                                                                         |
| T  |  X   | Player.outOfCreatures when not out of creatures                                                                                                     |
| T  |  X   | PlacementPlayerHasCreature.test when player has creature                                                                                            |
| T  |  X   | PlacementPlayerHasCreature.test when player does not have creature                                                                                  |
| T  |  X   | PlacementPlayerHasCreature.test when creature not in creatureCount map                                                                              |
| T  |  X   | PlacementConnected.test when placement not connected                                                                                                |
| T  |  X   | PlacementConnected.test when placement connected                                                                                                    |
| T  |  X   | GameManager.placeCreature when creature doesnt exist                                                                                                |
| T  |  X   | GameManager.placeCreature when creature does exist                                                                                                  |
| T  |  X   | GameManager.placeCreature when opening placement not connected                                                                                      |
| T  |  X   | GameManager.placeCreature when opening placement connected                                                                                          |
| T  |  X   | GameManager.placeCreature when opening placement on top of intial placement                                                                         |
| T  |  X   | GameManager.placeCreature when opening player does not have creature                                                                                |
| T  |  X   | GameManager.placeCreature when player has depleted their creature count                                                                             |
| T  |  X   | GameManager.placeCreature when placement is disconnected                                                                                            |
| T  |  X   | GameManager.placeCreature when placement next to enemy                                                                                              |
| R  |  X   | Refactor placeCreature to avoid multiple returns                                                                                                    |
| T  |  X   | GameManager.moveCreature when creature not at point                                                                                                 |
| T  |  X   | GameManager.moveCreature when trying to move creature in opening                                                                                    |
| T  |  X   | GameManager.moveCreature properly kamikazes creatures                                                                                               |
| T  |  X   | GameManager.moveCreature properly handles kamikaze to empty location                                                                                |
| T  |  X   | GameManager.moveCreature player must place butterfly after being kamikazed                                                                          |
| R  |  X   | Extract butterfly placement conditions into mustPlaceButterfly helper                                                                               | 
| T  |  X   | GameManager.moveCreature properly swaps creatures                                                                                                   |
| R  |  X   | Refactor moveCreature to avoid multiple returns                                                                                                     | 
| T  |  X   | GameManager game end by surrounding butterfly                                                                                                       |
| T  |  X   | GameManager game draw by double surrounded butterfly                                                                                                |
| T  |  X   | GameManager game end by no available moves or placements                                                                                            |
| T  |  X   | PreMoveDestinationNotButterfly.test when destination is not butterfly                                                                               |
| T  |  X   | PreMoveDestinationNotButterfly.test when destination is butterfly                                                                                   |
| T  |  X   | GameManager.moveCreature when destination is disconnected                                                                                           |
| T  |  X   | GameManager.moveCreature when destination is occupied                                                                                               |
| T  |  X   | GameManager.moveCreature when destination is not inline to origin                                                                                   |
| T  |  X   | GameManager.moveCreature when destination of a swap is a butterfly                                                                                  |
| T  |  X   | GameManager.moveCreature when destination is not removable for a kamikaze                                                                           |

# Design Patterns

## Factory Pattern

### CreatureFactory

The idea of the CreatureFactory is to abstract the process of creating creatures. The main abstraction is
the hiding of various creature configurations from the consumer of CreatureFactory. CreatureFactory consumers
need not know how each CreatureName is configured, just that they would like to create an instance of one.
This helps maintain SRP.

## Builder Pattern

### HexAroundGameBuilder

The process of creating an instance of a HexAroundGameManager from a configuration is non-trivial, and doing so
requires I/O operations and interacting with a configuration parser. Because HexAroundGameManager is a complex object,
and building it from a configuration is a complex task, it makes sense to delegate this responsibility to a
Builder pattern.

## Strategy Pattern

### ICondition, Validator, and Pathfinder

The strategy pattern is employed to create a generic pathfinder that can find paths under a variety of movement
constraints.
Because there are so many movement combinations it does not make sense to create a pathfinder for each one, rather
create
one pathfinder that accepts different movement condition algorithms. Therefore, IConditions are created for each
movement
constraint, and checked via a Validator,

## State Pattern

### HexAroundGameManager, Opening and PostOpening PlacementValidator

A state pattern similar to the example given in the textbook is used in HexAroundGameManager to manage placement
validation.
The state pattern is appropriate because the specific rules for validating a given placement changes over the lifetime
of the game.
Therefore, two distinct PlacementValidators are created and set from the game manager based upon the stage of the game.



