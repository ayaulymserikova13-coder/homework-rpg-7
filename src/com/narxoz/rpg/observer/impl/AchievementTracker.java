package com.narxoz.rpg.observer.impl;

import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;

import java.util.HashSet;
import java.util.Set;

public class AchievementTracker implements GameObserver {

    private final Set<String> unlocked = new HashSet<>();
    private int totalAttacks = 0;
    private int heroDeaths = 0;

    @Override
    public void onEvent(GameEvent event) {
        switch (event.getType()) {
            case ATTACK_LANDED:
                totalAttacks++;
                unlock("First Blood", totalAttacks >= 1);
                unlock("Relentless Assault", totalAttacks >= 10);
                break;

            case HERO_DIED:
                heroDeaths++;
                break;

            case BOSS_DEFEATED:
                unlock("Boss Slayer", true);
                unlock("No One Left Behind", heroDeaths == 0);
                break;

            default:
                break;
        }
    }

    private void unlock(String achievementName, boolean condition) {
        if (condition && unlocked.add(achievementName)) {
            System.out.println("[AchievementTracker] Achievement unlocked: " + achievementName);
        }
    }
}