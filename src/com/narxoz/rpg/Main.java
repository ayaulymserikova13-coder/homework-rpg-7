package com.narxoz.rpg;

import com.narxoz.rpg.boss.DungeonBoss;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.engine.DungeonEngine;
import com.narxoz.rpg.engine.EncounterResult;
import com.narxoz.rpg.observer.EventPublisher;
import com.narxoz.rpg.observer.impl.AchievementTracker;
import com.narxoz.rpg.observer.impl.BattleLogger;
import com.narxoz.rpg.observer.impl.HeroStatusMonitor;
import com.narxoz.rpg.observer.impl.LootDropper;
import com.narxoz.rpg.observer.impl.PartySupport;
import com.narxoz.rpg.strategy.AggressiveStrategy;
import com.narxoz.rpg.strategy.BalancedStrategy;
import com.narxoz.rpg.strategy.DefensiveStrategy;

import java.util.Arrays;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        EventPublisher publisher = new EventPublisher();

        Hero warrior = new Hero("Aldric", 95, 22, 10, new AggressiveStrategy());
        Hero guardian = new Hero("Brina", 120, 16, 15, new DefensiveStrategy());
        Hero ranger = new Hero("Cyra", 90, 18, 11, new BalancedStrategy());

        List<Hero> heroes = Arrays.asList(warrior, guardian, ranger);

        DungeonBoss boss = new DungeonBoss("The Cursed Warden", 260, 24, 10, publisher);

        BattleLogger battleLogger = new BattleLogger();
        AchievementTracker achievementTracker = new AchievementTracker();
        PartySupport partySupport = new PartySupport(heroes);
        HeroStatusMonitor heroStatusMonitor = new HeroStatusMonitor(heroes);
        LootDropper lootDropper = new LootDropper();

        publisher.registerObserver(battleLogger);
        publisher.registerObserver(achievementTracker);
        publisher.registerObserver(partySupport);
        publisher.registerObserver(heroStatusMonitor);
        publisher.registerObserver(lootDropper);
        publisher.registerObserver(boss); // boss is also an observer

        DungeonEngine engine = new DungeonEngine(heroes, boss, publisher, 20);
        EncounterResult result = engine.runEncounter();

        System.out.println("\n========== ENCOUNTER RESULT ==========");
        System.out.println("Heroes won: " + result.isHeroesWon());
        System.out.println("Rounds played: " + result.getRoundsPlayed());
        System.out.println("Surviving heroes: " + result.getSurvivingHeroes());
    }
}