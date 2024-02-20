# TDD Tests and refactorings

| ID | Done | Description                                                                                                                     |
|:--:|:----:|:--------------------------------------------------------------------------------------------------------------------------------|
| T  |  X   | Return instance of ICreature on call to CreatureFactory.make()                                                                  | 
| R  |  X   | Add utility function to load configs for factory testing                                                                        | 
| T  |  X   | Return instance of ICreature with correct name on call to CreatureFactory.make()                                                | 
| R  |  X   | Refactor Creature constructor to take existing CreatureDefinition record                                                        |
| T  |  X   | Return instance of ICreature with correct maxDistance on call to CreatureFactory.make()                                         |
| R  |  X   | Change creature factory to use map instead of list for lookup - intention is that each CreatureName only maps to one definition |
| T  |  X   | Return instance of ICreature with correct properties on call to CreatureFactory.make()                                          | 
| T  |  X   | isOccupied when location is not occupied                                                                                        | 
| T  |  X   | isOccupied when location is occupied                                                                                            |
| T  |  X   | getCreatureAt when board space is not occupied                                                                                  | 
| T  |  X   | getCreatureAt when board space is occupied                                                                                      |
| T  |  X   | hasProperty when creature does not have property                                                                                | 
| T  |  X   | hasProperty when creature has property                                                                                          | 
| T  |  X   | canReach when creature cannot reach                                                                                             | 
| R  |  X   | Change variable maxRange -> maxDistance to be more consistent with CreatureDefinition                                           |
| T  |  X   | canReach when creature can reach                                                                                                | 
| R  |  X   | Move basic functionality for getting/putting ICreatures on board into seperate Board class                                      |
| T  |  X   | board.getAllCreatures when no creatures at point                                                                                | 
| T  |  X   | board.getAllCreatures when creatures at point                                                                                   |
| T  |  X   | board.getCreatureWithName when no creatures at point                                                                            |
| T  |  X   | board.getCreatureWithName when no creature with name at point                                                                   |
| T  |  X   | board.getCreatureWithName when there is a match at the point                                                                    |
| T  |  X   | board.getTopCreature when there are no creatures at point                                                                       |
| T  |  X   | board.getTopCreature when there are creatures at point                                                                          |
| T  |  X   | Use @BeforeEach directive to make testing more concise                                                                          | 
| T  |  X   | board.placeCreature when no creatures at point                                                                                  |
| T  |  X   | board.placeCreature when already creatures at point                                                                             |
| T  |  X   | board.removeCreature when no creatures at point                                                                                 |
| T  |  X   | board.removeCreature when no creatures with name at point                                                                       |
| T  |  X   | board.removeCreature there is a match at the point                                                                              |
| T  |  X   | board.removeCreature there is a match at the point and removing the creature leaves the point empty                             |
| T  |  X   | board.moveCreature from an empty point                                                                                          |
| T  |  X   | board.moveCreature when no creature with name at point                                                                          |
| T  |  X   | board.moveCreature when there is a match at the point                                                                           |
| R  |  X   | Move calculateDistance helper from GameManager to Board                                                                         |
| T  |  X   | board.moveIsDisconnecting when move is not disconnecting                                                                        |
| T  |  X   | board.moveIsDisconnecting when creature being moved is the only one on board                                                    |
| R  |  X   | Move getting total creatures operation to private helper                                                                        |
| T  |  X   | board.moveIsDisconnecting when move is disconnecting                                                                            |
| R  |  X   | Create UnitVector and Vectors classes to allow for easier testing/more semantic access to cardinal directions                   |
| T  |  X   | board.placementIsDisconnecting when placement is not disconnecting                                                              |
| T  |  X   | board.placementIsDisconnecting when placement is the first in the game                                                          |
| T  |  X   | board.placementISDisconnecting when placement is disconnecting                                                                  |
| R  |  X   | Prefer inbuilt Java getOrDefault as opposed to containsKey checking for Board.getAllCreatures()                                 |
| T  |  X   | GameManager.placeCreature returns legal MoveResponse                                                                            |
| R  |  X   | Prefer Java Optionals vs. null returns to force consumers to deal with null case                                                |
| T  |  X   | GameManager.moveCreature returns legal MoveResponse                                                                             |
| T  |  X   | Creature is actually moved on board after moveCreature                                                                          |
| R  |  X   | Make calculateDistance return type double -> int since distances will always be integers                                        |
| T  |  X   | GameManager.moveCreature returns move error on disconnecting move                                                               |
| R  |  X   | Move functionality for calculating distances/getting neighbors into HexPoint class                                              | 
| T  |  X   | getNeighboringPoints of hex point                                                                                               | 
| T  |  X   | calculateDistanceTo own hex point                                                                                               | 
| T  |  X   | calculateDistanceTo another hex point                                                                                           | 
| T  |  X   | clone HexPoint                                                                                                                  | 

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



