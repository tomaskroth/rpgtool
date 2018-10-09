package com.tk.rpg.domain;

import java.util.Arrays;

public enum ItemType {
    ARMOR(8),
    WEAPON(5),
    WONDROUS(3),
    SCROLL(1),
    POTION(1);

    private final int budgetCost;

    ItemType(int budgetCost) {
        this.budgetCost = budgetCost;
    }

    public int getBudgetCost() {
        return this.budgetCost;
    }

    public static ItemType fromString(final String itemType) {
        String[] armorTypes = {"MA", "HA", "S", "LA"};
        String[] weaponTypes = {"A", "M", "R", "ST"};
        String[] wondrousTypes = {"W", "RD", "RG", "G", "WD"};

        if (itemType.equals("P")) {
            return POTION;
        }
        else if(itemType.equals("SC")) {
            return SCROLL;
        }
        else if (Arrays.asList(armorTypes).contains(itemType)) {
            return ARMOR;
        }
        else if (Arrays.asList(wondrousTypes).contains(itemType)){
            return WONDROUS;
        }
        else if(Arrays.asList(weaponTypes).contains(itemType)) {
            return WEAPON;
        }
        else {
            return WONDROUS;
        }
    }

}