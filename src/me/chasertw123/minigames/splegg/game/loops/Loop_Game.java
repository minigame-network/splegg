package me.chasertw123.minigames.splegg.game.loops;

import me.chasertw123.minigames.splegg.Main;
import me.chasertw123.minigames.splegg.game.modes.GameEndReason;
import me.chasertw123.minigames.splegg.game.states.GameState;

/**
 * Created by Scott Hiett on 8/7/2017.
 */
public class Loop_Game extends GameLoop {

    public Loop_Game(int time) {
        super(60 * time, 20);
    }

    @Override
    public void run() {
        if (Main.getInstance().gameManager.getGameState() == GameState.GAME)
            Main.getInstance().gameManager.checkGame();

        if (interval-- == 0) {
            Main.getInstance().gameManager.endGame(GameEndReason.TIME_UP);
        }
    }

}
