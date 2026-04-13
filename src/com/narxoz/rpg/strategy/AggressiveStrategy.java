package com.narxoz.rpg.strategy;

public class AggressiveStrategy implements CombatStrategy {

    @Override
    public int calculateDamage(int basePower) {
        return (int) Math.round(basePower * 1.5);
    }

    @Override
    public int calculateDefense(int baseDefense) {
        return Math.max(0, (int) Math.round(baseDefense * 0.7));
    }

    @Override
    public String getName() {
        return "Aggressive";
    }
}