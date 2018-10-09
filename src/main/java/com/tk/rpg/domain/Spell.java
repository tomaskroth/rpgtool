package com.tk.rpg.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Spell {

    private final Integer level;
    private final String name;

}
