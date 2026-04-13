package com.narxoz.rpg.strategy;

public class BossPhaseOneStrategy implements CombatStrategy {

    @Override
    public int calculateDamage(int basePower) {
        return (int) Math.round(basePower * 1.0);
    }

    @Override
    public int calculateDefense(int baseDefense) {
        return (int) Math.round(baseDefense * 1.2);
    }

    @Override
    public String getName() {
        return "Phase 1 - Measured Tyranny";
    }
}