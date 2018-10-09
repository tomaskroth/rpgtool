package com.tk.rpg.repository;

import com.tk.rpg.domain.Item;
import com.tk.rpg.domain.ItemType;
import com.tk.rpg.domain.Rarity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class MagicItems {

    private static final Map<ItemType, Map<Rarity, List<Item>>> items;

    static {
        items = new HashMap<>();
    }

    public void setUp(Map<ItemType, List<Item>> map) {
        map.keySet().forEach(key -> {
            Map<Rarity, List<Item>> collect = map.get(key).stream().collect(Collectors.groupingBy(Item::getRarity));
            items.put(key, collect);
        });
    }

    public Map<Rarity, List<Item>> getItemsOfType(final ItemType itemType) {
        return items.get(itemType);
    }

}
