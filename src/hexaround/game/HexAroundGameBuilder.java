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
        HexAroundFirstSubmission gameManager = new HexAroundFirstSubmission();    // an empty game manager

        Collection<PlayerConfiguration> playerConfigurations = configuration.players();
        Collection<Player> players = constructPlayers(playerConfigurations);
        gameManager.setPlayers(players);

        Map<CreatureName, CreatureDefinition> creatureDefinitions = new HashMap<>();
        for (CreatureDefinition creatureDefinition : configuration.creatures()) {
            creatureDefinitions.put(creatureDefinition.name(), creatureDefinition);
        }
        CreatureFactory creatureFactory = new CreatureFactory(creatureDefinitions);
        gameManager.setCreatureFactory(creatureFactory);

        IBoard board = new Board(new HashMap<>());
        gameManager.setBoard(board);

        return gameManager;
    }

    private static Collection<Player> constructPlayers(Collection<PlayerConfiguration> playerConfigurations) {
        Collection<Player> players = new ArrayList<>();

        for (PlayerConfiguration playerConfiguration : playerConfigurations) {
            PlayerName name = playerConfiguration.Player();
            Map<CreatureName, Integer> creatures = playerConfiguration.creatures();

            Player player = new Player(name, creatures);

            players.add(player);
        }

        return players;
    }
}
