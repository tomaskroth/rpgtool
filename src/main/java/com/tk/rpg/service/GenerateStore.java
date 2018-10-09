package com.tk.rpg.service;

import com.tk.rpg.domain.Item;
import com.tk.rpg.domain.ItemType;
import com.tk.rpg.domain.Rarity;
import com.tk.rpg.dto.StoreDefinition;
import com.tk.rpg.repository.MagicItems;
import com.tk.rpg.repository.Spells;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GenerateStore {

    private final MagicItems magicItems;
    private final Spells spells;

    public List<Item> generateStore(StoreDefinition storeDefinition) {

        if (isInvalid(storeDefinition)) {
            return null;
        }

        Map<ItemType, List<Item>> stock = new HashMap<>();
        int consumedBudget = 0;
        while (consumedBudget < storeDefinition.getSize().getBudget()) {
            ItemType itemType = generateRandomType(storeDefinition);
            Map<Rarity, List<Item>> itemsOfType = magicItems.getItemsOfType(itemType);

            int percentile = randomWithRange(1, 100);
            Rarity rarity = storeDefinition.getWealth().getRarity(percentile);

            List<Item> items = itemsOfType.get(rarity);

            if (items == null) {
                continue;
            }

            int itemIndex = randomWithRange(0, items.size() - 1);

            stock.computeIfAbsent(itemType, k -> new ArrayList<>());
            Item item = items.get(itemIndex);

            if (itemType == ItemType.SCROLL && item.getName().contains("Spell Scroll")) {
                String spellName = getRandomLeveledSpell(item);
                item.setName(String.format(item.getName(), spellName));
            }

            stock.get(itemType).add(item);
            consumedBudget += itemType.getBudgetCost();
        }

        List<Item> returnList = new ArrayList<>();
        for (List<Item> list : stock.values()) {
            for (Item item : list) {
                if (returnList.contains(item)) {
                    Item actualItem = returnList.get(returnList.indexOf(item));
                    actualItem.setQuantity(actualItem.getQuantity() + 1);
                } else {
                    item.setQuantity(1);
                    returnList.add(item);
                }
            }
        }

        return returnList;
    }

    private boolean isInvalid(StoreDefinition storeDefinition) {
        return !storeDefinition.isScrolls() && !storeDefinition.isPotions() && !storeDefinition.isWondrous() && !storeDefinition.isWeapons() && !storeDefinition.isArmors();
    }

    private String getRandomLeveledSpell(Item item) {
        String[] split = item.getName().split("\\(");
        String levelString = split[1].substring(0, 1);
        Integer level = Integer.valueOf(levelString);

        List<String> spellsOfLevel = spells.getSpellsOfLevel(level);
        int index = randomWithRange(0, spellsOfLevel.size() - 1);
        return spellsOfLevel.get(index);
    }


    private ItemType generateRandomType(StoreDefinition storeDefinition) {
        int number = randomWithRange(1, 5);
        ItemType type = validNumber(storeDefinition, number);
        if (type == null) {
            return generateRandomType(storeDefinition);
        } else {
            return type;
        }
    }

    private ItemType validNumber(StoreDefinition storeDefinition, int number) {
        if (storeDefinition.isArmors() && number == 1) {
            return ItemType.ARMOR;
        }

        if (storeDefinition.isWeapons() && number == 2) {
            return ItemType.WEAPON;
        }

        if (storeDefinition.isWondrous() && number == 3) {
            return ItemType.WONDROUS;
        }

        if (storeDefinition.isPotions() && number == 4) {
            return ItemType.POTION;
        }

        if (storeDefinition.isScrolls() && number == 5) {
            return ItemType.SCROLL;
        }

        return null;
    }

    private int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

}
