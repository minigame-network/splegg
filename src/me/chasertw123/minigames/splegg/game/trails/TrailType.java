package me.chasertw123.minigames.splegg.game.trails;

/**
 * Created by Scott Hiett on 8/11/2017.
 */
public enum TrailType {

    NONE(new Trail_None()),
    ARTIST(new Trail_Artist()),
    LOVER(new Trail_Lover());

    private Trail trail;

    TrailType(Trail trail) {
        this.trail = trail;
    }

    public Trail getTrail() {
        return trail;
    }

}
