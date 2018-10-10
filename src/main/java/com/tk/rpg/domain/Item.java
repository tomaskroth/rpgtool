package com.tk.rpg.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Item implements Cloneable{
    private String name;
    private Rarity rarity;
    private ItemType type;
    private Integer price;

    @EqualsAndHashCode.Exclude
    private Integer quantity = 1;

    public Item(final ItemRaw itemRaw) {
        this.name = itemRaw.getName();
        this.rarity = Rarity.valueOf(itemRaw.getRarity());
        this.type = ItemType.valueOf(itemRaw.getType());
    }

    public Item(String name, Rarity rarity, ItemType type, Integer price) {
        this.name = name;
        this.rarity = rarity;
        this.type = type;
        this.price = price;
    }

    @Override
    public Item clone() {
        return new Item(name, rarity, type, price);
    }
}
