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
| R  |  X   | Prefer inbuilt Java getOrDefault as opposed to containsKey checking for Board.get()                                             |

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



