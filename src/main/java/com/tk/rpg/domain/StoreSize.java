package com.tk.rpg.domain;

public enum StoreSize {

    TINY(20),
    SMALL(40),
    MEDIUM(70),
    BIG(100);


    private final int budget;

    StoreSize(int budget) {
        this.budget = budget;
    }

    public int getBudget() {
        return this.budget;
    }
}
