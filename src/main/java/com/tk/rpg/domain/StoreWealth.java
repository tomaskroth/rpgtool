package com.tk.rpg.domain;

public enum StoreWealth {
    //Common, Uncommon, Rare, Very Rare, Legendary, Artifact
    POOR(70, 30, 0, 0, 0),
    MEDIUM(40, 35, 20, 5, 0),
    RICH(20, 40, 28, 10, 2),
    VERY_RICH(10, 30, 35, 20, 5);


    private int common;
    private int uncommon;
    private int rare;
    private int veryRare;
    private int legendary;


    StoreWealth(int common, int uncommon, int rare, int veryRare, int legendary) {
        this.common = common;
        this.uncommon = this.common + uncommon;
        this.rare = this.uncommon + rare;
        this.veryRare = this.rare + veryRare;
        this.legendary = this.veryRare + legendary;
    }

    public Rarity getRarity(int percentile) {
        if (percentile <= common) {
            return Rarity.COMMON;
        }
        if (percentile <= uncommon) {
            return Rarity.UNCOMMON;
        }
        if (percentile <= rare) {
            return Rarity.RARE;
        }
        if (percentile <= veryRare) {
            return Rarity.VERY_RARE;
        }
        if (percentile <= legendary) {
            return Rarity.LEGENDARY;
        }

        return Rarity.COMMON;
    }
}
