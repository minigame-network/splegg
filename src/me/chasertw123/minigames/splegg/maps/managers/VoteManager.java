package me.chasertw123.minigames.splegg.maps.managers;

import me.chasertw123.minigames.splegg.Main;
import me.chasertw123.minigames.splegg.maps.BaseMap;
import me.chasertw123.minigames.splegg.users.SpleggPlayer;

import java.util.Random;

/**
 * Created by Chase on 2/18/2017.
 */
public class VoteManager {

    private boolean votingActive = true;

    public int getVotes(BaseMap map) {
        int count = 0;
        for (SpleggPlayer user : Main.getInstance().spleggPlayerManager.toCollection())
            if (user.hasVoted() && user.getVotedMap().equalsIgnoreCase(map.getName()))
                count++;

        return count;
    }

    public boolean isVotingActive() {
        return votingActive;
    }

    public void setVotingActive(boolean votingActive) {
        this.votingActive = votingActive;
    }

    public String getWinner() {
        int highestCount = 0;
        String highestName = Main.getInstance().mapManager.getMaps().get(new Random().nextInt(Main.getInstance().mapManager.getMaps().size())).getName();

        for (BaseMap s : Main.getInstance().mapManager.getMaps()) {
            if (getVotes(s) > highestCount) {
                highestCount = getVotes(s);
                highestName = s.getName();
            }
        }

        setVotingActive(false);

        return highestName;
    }

}
