package com.narxoz.rpg.boss;

import com.narxoz.rpg.observer.EventPublisher;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;
import com.narxoz.rpg.strategy.BossPhaseOneStrategy;
import com.narxoz.rpg.strategy.BossPhaseThreeStrategy;
import com.narxoz.rpg.strategy.BossPhaseTwoStrategy;
import com.narxoz.rpg.strategy.CombatStrategy;

public class DungeonBoss implements GameObserver {

    private final String name;
    private int hp;
    private final int maxHp;
    private final int attackPower;
    private final int defense;
    private int currentPhase;

    private CombatStrategy strategy;

    private final CombatStrategy phaseOneStrategy;
    private final CombatStrategy phaseTwoStrategy;
    private final CombatStrategy phaseThreeStrategy;

    private final EventPublisher publisher;

    public DungeonBoss(String name, int hp, int attackPower, int defense, EventPublisher publisher) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.publisher = publisher;

        this.phaseOneStrategy = new BossPhaseOneStrategy();
        this.phaseTwoStrategy = new BossPhaseTwoStrategy();
        this.phaseThreeStrategy = new BossPhaseThreeStrategy();

        this.currentPhase = 1;
        this.strategy = phaseOneStrategy;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getDefense() {
        return defense;
    }

    public int getCurrentPhase() {
        return currentPhase;
    }

    public CombatStrategy getStrategy() {
        return strategy;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public int calculateDamage() {
        return strategy.calculateDamage(attackPower);
    }

    public int calculateDefense() {
        return strategy.calculateDefense(defense);
    }

    public void takeDamage(int amount) {
        if (amount <= 0 || !isAlive()) {
            return;
        }

        int previousPhase = determinePhase(hp);
        hp = Math.max(0, hp - amount);
        int newPhase = determinePhase(hp);

        if (previousPhase < 2 && newPhase >= 2) {
            publisher.notifyObservers(new GameEvent(GameEventType.BOSS_PHASE_CHANGED, name, 2));
        }
        if (previousPhase < 3 && newPhase >= 3) {
            publisher.notifyObservers(new GameEvent(GameEventType.BOSS_PHASE_CHANGED, name, 3));
        }
    }

    private int determinePhase(int currentHp) {
        double percent = (currentHp * 100.0) / maxHp;
        if (percent <= 30.0) {
            return 3;
        }
        if (percent <= 60.0) {
            return 2;
        }
        return 1;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() != GameEventType.BOSS_PHASE_CHANGED) {
            return;
        }

        if (!name.equals(event.getSourceName())) {
            return;
        }

        int targetPhase = event.getValue();
        if (targetPhase <= currentPhase) {
            return;
        }

        currentPhase = targetPhase;

        switch (currentPhase) {
            case 2:
                strategy = phaseTwoStrategy;
                break;
            case 3:
                strategy = phaseThreeStrategy;
                break;
            default:
                strategy = phaseOneStrategy;
        }

        System.out.println("[Boss] " + name + " shifts into Phase " + currentPhase
                + " and now uses strategy: " + strategy.getName());
    }
}
