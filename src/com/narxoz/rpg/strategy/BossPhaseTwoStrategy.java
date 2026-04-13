package com.narxoz.rpg.strategy;

public class BossPhaseTwoStrategy implements CombatStrategy {

    @Override
    public int calculateDamage(int basePower) {
        return (int) Math.round(basePower * 1.35);
    }

    @Override
    public int calculateDefense(int baseDefense) {
        return (int) Math.round(baseDefense * 0.95);
    }

    @Override
    public String getName() {
        return "Phase 2 - Savage Pressure";
    }
}