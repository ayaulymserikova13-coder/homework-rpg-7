package com.narxoz.rpg.observer.impl;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PartySupport implements GameObserver {

    private final List<Hero> heroes;
    private final Random random;
    private final int healAmount;

    public PartySupport(List<Hero> heroes) {
        this(heroes, 12, 7);
    }

    public PartySupport(List<Hero> heroes, int healAmount, long seed) {
        this.heroes = heroes;
        this.healAmount = healAmount;
        this.random = new Random(seed);
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() != GameEventType.HERO_LOW_HP) {
            return;
        }

        List<Hero> livingHeroes = new ArrayList<>();
        for (Hero hero : heroes) {
            if (hero.isAlive()) {
                livingHeroes.add(hero);
            }
        }

        if (livingHeroes.isEmpty()) {
            return;
        }

        Hero target = livingHeroes.get(random.nextInt(livingHeroes.size()));
        int before = target.getHp();
        target.heal(healAmount);
        int restored = target.getHp() - before;

        System.out.println("[PartySupport] Emergency aid restores " + restored
                + " HP to " + target.getName() + ".");
    }
}