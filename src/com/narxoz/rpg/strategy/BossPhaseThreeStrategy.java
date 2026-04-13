package com.narxoz.rpg.strategy;

public class BossPhaseThreeStrategy implements CombatStrategy {

    @Override
    public int calculateDamage(int basePower) {
        return (int) Math.round(basePower * 1.7);
    }

    @Override
    public int calculateDefense(int baseDefense) {
        return Math.max(0, (int) Math.round(baseDefense * 0.35));
    }

    @Override
    public String getName() {
        return "Phase 3 - Desperate Ruin";
    }
}