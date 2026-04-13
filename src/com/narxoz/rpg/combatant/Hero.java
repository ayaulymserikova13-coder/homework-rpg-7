package com.narxoz.rpg.combatant;

import com.narxoz.rpg.strategy.CombatStrategy;
import com.narxoz.rpg.strategy.BalancedStrategy;

public class Hero {

    private final String name;
    private int hp;
    private final int maxHp;
    private final int attackPower;
    private final int defense;
    private CombatStrategy strategy;

    public Hero(String name, int hp, int attackPower, int defense) {
        this(name, hp, attackPower, defense, new BalancedStrategy());
    }

    public Hero(String name, int hp, int attackPower, int defense, CombatStrategy strategy) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.strategy = strategy == null ? new BalancedStrategy() : strategy;
    }

    public String getName()        { return name; }
    public int getHp()             { return hp; }
    public int getMaxHp()          { return maxHp; }
    public int getAttackPower()    { return attackPower; }
    public int getDefense()        { return defense; }
    public boolean isAlive()       { return hp > 0; }

    public CombatStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(CombatStrategy strategy) {
        if (strategy != null) {
            this.strategy = strategy;
        }
    }

    public int calculateDamage() {
        return strategy.calculateDamage(attackPower);
    }

    public int calculateDefense() {
        return strategy.calculateDefense(defense);
    }

    public void takeDamage(int amount) {
        hp = Math.max(0, hp - Math.max(0, amount));
    }

    public void heal(int amount) {
        hp = Math.min(maxHp, hp + Math.max(0, amount));
    }
}
