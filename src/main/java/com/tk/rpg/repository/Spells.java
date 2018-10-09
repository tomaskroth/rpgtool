package com.tk.rpg.repository;

import com.tk.rpg.domain.Spell;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class Spells {

    private static final Map<Integer, List<String>> spells;

    static {
        spells = new HashMap<>();
    }

    public void setUp(List<Spell> spellList) {
        spellList.forEach(s -> {
            spells.computeIfAbsent(s.getLevel(), v -> new ArrayList<>());
            spells.get(s.getLevel()).add(s.getName());
        });
    }

    public List<String> getSpellsOfLevel(final Integer level) {
        return spells.get(level);
    }

}
