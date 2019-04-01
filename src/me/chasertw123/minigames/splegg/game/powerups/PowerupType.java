package me.chasertw123.minigames.splegg.game.powerups;

/**
 * Created by Scott Hiett on 8/8/2017.
 */
public enum PowerupType {

    RAPID_FIRE (new Powerup_RapidFire()),
    SCATTER_SHOT (new Powerup_ScatterShot()),
    SPLATTER_AMMO (new Powerup_SplatterAmmo()),
    SUPER_SPEED (new Powerup_SuperSpeed()),
    INVISIBILITY (new Powerup_Invisibility()),
    TNT_RAIN (new Powerup_TNTRain()),
    EXPLOSIVE_EGGS (new Powerup_ExplosiveEggs());

    private Powerup powerupClass;

    PowerupType(Powerup powerupClass){
        this.powerupClass = powerupClass;
    }

    public Powerup getPowerupClass() {
        return powerupClass;
    }

}
