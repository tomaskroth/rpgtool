package com.tk.rpg.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ItemRaw {
    private String name;
    private String rarity;
    private String type;
    private String magic;
}
