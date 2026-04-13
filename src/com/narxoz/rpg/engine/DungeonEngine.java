package com.narxoz.rpg.engine;

import com.narxoz.rpg.boss.DungeonBoss;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.observer.EventPublisher;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.strategy.DefensiveStrategy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DungeonEngine {

    private final List<Hero> heroes;
    private final DungeonBoss boss;
    private final EventPublisher publisher;
    private final int maxRounds;

    private final Set<String> lowHpTriggered = new HashSet<>();
    private boolean strategySwitched = false;

    public DungeonEngine(List<Hero> heroes, DungeonBoss boss, EventPublisher publisher, int maxRounds) {
        this.heroes = heroes;
        this.boss = boss;
        this.publisher = publisher;
        this.maxRounds = maxRounds;
    }

    public EncounterResult runEncounter() {
        int roundsPlayed = 0;

        while (boss.isAlive() && hasLivingHeroes() && roundsPlayed < maxRounds) {
            roundsPlayed++;
            System.out.println("\n========== ROUND " + roundsPlayed + " ==========");
            System.out.println("Boss HP: " + boss.getHp() + "/" + boss.getMaxHp()
                    + " | Strategy: " + boss.getStrategy().getName());

            if (!strategySwitched && roundsPlayed == 4 && heroes.get(0).isAlive()) {
                heroes.get(0).setStrategy(new DefensiveStrategy());
                strategySwitched = true;
                System.out.println("[Engine] " + heroes.get(0).getName()
                        + " switches strategy mid-battle to: "
                        + heroes.get(0).getStrategy().getName());
            }

            // Heroes attack boss
            for (Hero hero : heroes) {
                if (!hero.isAlive() || !boss.isAlive()) {
                    continue;
                }

                int rawDamage = hero.calculateDamage();
                int reducedByDefense = boss.calculateDefense();
                int finalDamage = Math.max(1, rawDamage - reducedByDefense);

                boss.takeDamage(finalDamage);
                publisher.notifyObservers(new GameEvent(
                        GameEventType.ATTACK_LANDED,
                        hero.getName(),
                        finalDamage
                ));

                System.out.println(hero.getName() + " attacks the boss using "
                        + hero.getStrategy().getName() + " for " + finalDamage + " damage."
                        + " Boss HP now: " + boss.getHp() + "/" + boss.getMaxHp());

                if (!boss.isAlive()) {
                    publisher.notifyObservers(new GameEvent(
                            GameEventType.BOSS_DEFEATED,
                            boss.getName(),
                            roundsPlayed
                    ));
                    break;
                }
            }

            if (!boss.isAlive()) {
                break;
            }

            // Boss attacks heroes
            for (Hero hero : heroes) {
                if (!hero.isAlive()) {
                    continue;
                }

                int rawDamage = boss.calculateDamage();
                int reducedByDefense = hero.calculateDefense();
                int finalDamage = Math.max(1, rawDamage - reducedByDefense);

                hero.takeDamage(finalDamage);
                publisher.notifyObservers(new GameEvent(
                        GameEventType.ATTACK_LANDED,
                        boss.getName(),
                        finalDamage
                ));

                System.out.println("Boss attacks " + hero.getName() + " using "
                        + boss.getStrategy().getName() + " for " + finalDamage + " damage."
                        + " " + hero.getName() + " HP now: " + hero.getHp() + "/" + hero.getMaxHp());

                if (hero.isAlive() && isLowHp(hero) && !lowHpTriggered.contains(hero.getName())) {
                    lowHpTriggered.add(hero.getName());
                    publisher.notifyObservers(new GameEvent(
                            GameEventType.HERO_LOW_HP,
                            hero.getName(),
                            hero.getHp()
                    ));
                }

                if (!hero.isAlive()) {
                    publisher.notifyObservers(new GameEvent(
                            GameEventType.HERO_DIED,
                            hero.getName(),
                            0
                    ));
                }
            }
        }

        boolean heroesWon = !boss.isAlive();
        int survivingHeroes = countLivingHeroes();

        if (roundsPlayed >= maxRounds && boss.isAlive() && hasLivingHeroes()) {
            System.out.println("\n[Engine] Max rounds reached. Encounter ends to prevent infinite combat.");
        }

        return new EncounterResult(heroesWon, roundsPlayed, survivingHeroes);
    }

    private boolean hasLivingHeroes() {
        for (Hero hero : heroes) {
            if (hero.isAlive()) {
                return true;
            }
        }
        return false;
    }

    private int countLivingHeroes() {
        int count = 0;
        for (Hero hero : heroes) {
            if (hero.isAlive()) {
                count++;
            }
        }
        return count;
    }

    private boolean isLowHp(Hero hero) {
        return hero.getHp() > 0 && hero.getHp() <= hero.getMaxHp() * 0.3;
    }
}