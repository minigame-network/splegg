package me.chasertw123.minigames.splegg.game.modes;

/**
 * Created by ScottsCoding on 12/08/2017.
 */
public enum SpleggGameMode {

    STANDARD (new SpleggGame_Standard()),
    PAINT_WORLD (new SpleggGame_PaintWorld());

    private AbstractSpleggGameMode spleggGameMode;

    SpleggGameMode(AbstractSpleggGameMode gameMode) {
        this.spleggGameMode = gameMode;
    }

    public AbstractSpleggGameMode getGameModeClass() {
        return spleggGameMode;
    }

}
