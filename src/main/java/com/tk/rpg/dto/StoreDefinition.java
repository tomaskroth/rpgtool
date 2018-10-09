package com.tk.rpg.dto;

import com.tk.rpg.domain.StoreSize;
import com.tk.rpg.domain.StoreWealth;
import lombok.Data;

@Data
public class StoreDefinition {

    private StoreWealth wealth;
    private StoreSize size;
    private boolean weapons;
    private boolean armors;
    private boolean wondrous;
    private boolean scrolls;
    private boolean potions;

}
