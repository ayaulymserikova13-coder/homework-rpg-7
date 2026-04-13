package com.narxoz.rpg.observer.impl;

import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;

public class BattleLogger implements GameObserver {

    @Override
    public void onEvent(GameEvent event) {
        GameEventType type = event.getType();

        switch (type) {
            case ATTACK_LANDED:
                System.out.println("[BattleLogger] " + event.getSourceName()
                        + " landed an attack for " + event.getValue() + " damage.");
                break;
            case HERO_LOW_HP:
                System.out.println("[BattleLogger] WARNING: " + event.getSourceName()
                        + " is low on HP (" + event.getValue() + " HP left).");
                break;
            case HERO_DIED:
                System.out.println("[BattleLogger] " + event.getSourceName() + " has fallen.");
                break;
            case BOSS_PHASE_CHANGED:
                System.out.println("[BattleLogger] Boss phase changed to Phase " + event.getValue() + ".");
                break;
            case BOSS_DEFEATED:
                System.out.println("[BattleLogger] Boss defeated on round " + event.getValue() + "!");
                break;
            default:
                System.out.println("[BattleLogger] Unknown event.");
        }
    }
}