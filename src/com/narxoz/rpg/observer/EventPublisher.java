package com.narxoz.rpg.observer;

import java.util.ArrayList;
import java.util.List;

public class EventPublisher {

    private final List<GameObserver> observers = new ArrayList<>();

    public void registerObserver(GameObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(GameEvent event) {
        for (GameObserver observer : observers) {
            observer.onEvent(event);
        }
    }
}