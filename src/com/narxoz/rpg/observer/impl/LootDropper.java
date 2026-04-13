package com.narxoz.rpg.observer.impl;

import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;

import java.util.Random;

public class LootDropper implements GameObserver {

    private final Random random = new Random(19);

    private static final String[] PHASE_TWO_LOOT = {
            "Ember Ring",
            "Iron Relic",
            "Shadow Potion"
    };

    private static final String[] PHASE_THREE_LOOT = {
            "Cursed Fang",
            "Blood Ruby",
            "Ancient Sigil"
    };

    private static final String[] FINAL_LOOT = {
            "Legendary Dungeon Core",
            "Crown of the Fallen King",
            "Mythic Soulblade"
    };

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.BOSS_PHASE_CHANGED) {
            if (event.getValue() == 2) {
                System.out.println("[LootDropper] Phase loot dropped: " + randomItem(PHASE_TWO_LOOT));
            } else if (event.getValue() == 3) {
                System.out.println("[LootDropper] Phase loot dropped: " + randomItem(PHASE_THREE_LOOT));
            }
        } else if (event.getType() == GameEventType.BOSS_DEFEATED) {
            System.out.println("[LootDropper] Final loot dropped: " + randomItem(FINAL_LOOT));
        }
    }

    private String randomItem(String[] items) {
        return items[random.nextInt(items.length)];
    }
}