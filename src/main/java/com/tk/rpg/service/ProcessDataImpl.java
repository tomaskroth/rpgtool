package com.tk.rpg.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.tk.rpg.BootstrapCLR;
import com.tk.rpg.domain.Item;
import com.tk.rpg.domain.ItemRaw;
import com.tk.rpg.domain.ItemType;
import com.tk.rpg.domain.Spell;
import com.tk.rpg.repository.MagicItems;
import com.tk.rpg.repository.Spells;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProcessDataImpl implements ProcessData {

    private final MagicItems magicItems;
    private final Spells spells;

    private static final Type LIST_ITEM_TYPE = new TypeToken<List<ItemRaw>>() {}.getType();
    private static final Type MAP_ITEM_TYPE = new TypeToken<Map<ItemType, List<Item>>>() {}.getType();
    private static final Type MAP_SPELL_TYPE = new TypeToken<List<Spell>>() {}.getType();

    private static final String ITEM_FILE_NAME = "items.json";
    private static final String SPELL_FILE_NAME = "spells.json";


    @Override
    public void initializeDatabase() throws IOException{
        Map<ItemType, List<Item>> itemsFromJson = getItemsFromJson();
        List<Spell> spellsFromJson = getSpellsFromJson();
        magicItems.setUp(itemsFromJson);
        spells.setUp(spellsFromJson);
    }

    @Override
    public void createDatabaseFromRawData() throws IOException {
        ArrayList<ItemRaw> data = getRawItemsFromJson();
        Map<ItemType, List<Item>> collect = processStructuredItems(data);
        saveItems(collect);
    }

    private InputStream loadFileFromResources(String fileName) {
        ClassLoader classLoader = BootstrapCLR.class.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }

    private List<Spell> getSpellsFromJson() throws IOException {
        InputStream file = loadFileFromResources(SPELL_FILE_NAME);
        List<Spell> data;
        try (JsonReader reader = new JsonReader(new InputStreamReader(file))) {
            Gson gson = new Gson();
            data = gson.fromJson(reader, MAP_SPELL_TYPE);
        }
        return data;
    }

    private Map<ItemType, List<Item>> getItemsFromJson() throws IOException {
        InputStream file = loadFileFromResources(ITEM_FILE_NAME);
        Map<ItemType, List<Item>> data;
        try (JsonReader reader = new JsonReader(new InputStreamReader(file))) {
            Gson gson = new Gson();
            data = gson.fromJson(reader, MAP_ITEM_TYPE);
        }
        return data;
    }

    private ArrayList<ItemRaw> getRawItemsFromJson() throws IOException {
        InputStream file = loadFileFromResources("rawItems.json");
        ArrayList<ItemRaw> data;
        try (JsonReader reader = new JsonReader(new InputStreamReader(file))) {
            Gson gson = new Gson();
            data = gson.fromJson(reader, LIST_ITEM_TYPE);
        }
        return data;
    }

    private Map<ItemType, List<Item>> processStructuredItems(ArrayList<ItemRaw> data) {
        return data.stream()
                .filter(m -> !StringUtils.isEmpty(m.getMagic()) && m.getMagic().equals("1"))
                .filter(m -> !StringUtils.isEmpty(m.getType()))
                .filter(m -> !StringUtils.isEmpty(m.getRarity()))
                .peek(itemRaw ->
                    itemRaw.setType(ItemType.fromString(itemRaw.getType()).toString())
                )
                .peek(itemRaw -> {
                    String formattedString = itemRaw.getRarity().toUpperCase().replace(" ", "_");
                    itemRaw.setRarity(formattedString);
                })
                .map(Item::new)
                .collect(Collectors.groupingBy(Item::getType));
    }

    private void saveItems(Map<ItemType, List<Item>> collect) throws IOException {
        Gson gson = new Gson();
        FileWriter fileWriter = new FileWriter(new File("src/main/resources/" + ITEM_FILE_NAME));
        gson.toJson(collect, fileWriter);
        fileWriter.close();
    }
}
