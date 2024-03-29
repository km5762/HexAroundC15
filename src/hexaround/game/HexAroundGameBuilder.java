/*
 * ******************************************************************************
 *  This files was developed for CS4233: Object-Oriented Analysis & Design.
 *  The course was taken at Worcester Polytechnic Institute.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  * Copyright ©2016-2017 Gary F. Pollice
 *  ******************************************************************************
 *
 * This class:
 * MUST be MODIFIED to create an instance of the IHexAroundGameManager interface.
 * MAY NOT be MOVED from this package.
 */

package hexaround.game;

import hexaround.config.*;
import hexaround.game.board.Board;
import hexaround.game.board.IBoard;
import hexaround.game.creature.CreatureFactory;
import hexaround.game.creature.CreatureName;
import hexaround.game.player.Player;
import hexaround.game.player.PlayerName;
import hexaround.game.rules.ICondition;
import hexaround.game.rules.Validator;
import hexaround.game.rules.placement.OpeningPlacementValidator;
import hexaround.game.rules.placement.PlacementContext;
import hexaround.game.rules.placement.PlacementEmpty;
import hexaround.game.rules.placement.PlacementNextToAllyAndNotEnemy;

import java.io.*;
import java.util.*;

public class HexAroundGameBuilder {
    /**
     * Builds an instance of HexAroundGameManager using the specified configuration file
     *
     * @param configurationFile the configuration file to be used to build the game manager
     * @return an instance of HexAroundGameManager
     * @throws IOException occurs when the specified configuration file cannot be read
     */
    public static IHexAroundGameManager buildGameManager(String configurationFile) throws IOException {
        HexAroundConfigurationMaker configurationMaker =
            new HexAroundConfigurationMaker(configurationFile);
        GameConfiguration configuration = configurationMaker.makeConfiguration();
        HexAroundGameManager gameManager = new HexAroundGameManager();    // an empty game manager

        Map<PlayerName, Player> players = constructPlayers(configuration.players());
        gameManager.setPlayers(players);

        CreatureFactory creatureFactory = constructCreatureFactory(configuration.creatures());
        gameManager.setCreatureFactory(creatureFactory);

        IBoard board = new Board(new HashMap<>());
        gameManager.setBoard(board);
        
        gameManager.setNameOfPlayerWithTurn(PlayerName.BLUE);

        Validator<PlacementContext> openingPlacementValidator = new OpeningPlacementValidator();
        gameManager.setPlacementValidator(openingPlacementValidator);

        gameManager.setTurnNumber(0);

        return gameManager;
    }

    private static CreatureFactory constructCreatureFactory(Collection<CreatureDefinition> creatureDefinitions) {
        Map<CreatureName, CreatureDefinition> creatureDefinitionsMap = new HashMap<>();

        for (CreatureDefinition creatureDefinition : creatureDefinitions) {
            creatureDefinitionsMap.put(creatureDefinition.name(), creatureDefinition);
        }

        return new CreatureFactory(creatureDefinitionsMap);
    }

    private static Map<PlayerName, Player> constructPlayers(Collection<PlayerConfiguration> playerConfigurations) {
        Map<PlayerName, Player> players = new HashMap<>();

        for (PlayerConfiguration playerConfiguration : playerConfigurations) {
            PlayerName name = playerConfiguration.Player();
            Map<CreatureName, Integer> creatures = playerConfiguration.creatures();

            Player player = new Player(name, creatures);

            players.put(name, player);
        }

        return players;
    }
}
