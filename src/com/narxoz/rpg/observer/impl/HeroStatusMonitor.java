package com.narxoz.rpg.observer.impl;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;

import java.util.List;

public class HeroStatusMonitor implements GameObserver {

    private final List<Hero> heroes;

    public HeroStatusMonitor(List<Hero> heroes) {
        this.heroes = heroes;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.HERO_LOW_HP || event.getType() == GameEventType.HERO_DIED) {
            printSummary();
        }
    }

    private void printSummary() {
        System.out.println("[HeroStatusMonitor] Party status:");
        for (Hero hero : heroes) {
            String status = hero.isAlive() ? "ALIVE" : "DEAD";
            System.out.println(" - " + hero.getName() + ": " + hero.getHp() + "/" + hero.getMaxHp()
                    + " HP, " + status + ", strategy=" + hero.getStrategy().getName());
        }
    }
}