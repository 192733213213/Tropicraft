package net.tropicraft.enums;

import net.minecraft.util.IStringSerializable;

public enum TropicraftFlowers implements IStringSerializable {
	
	COMMELINA_DIFFUSA, CROCOSMIA, ORCHID, CANNA, ANEMONE, ORANGE_ANTHURIUM, RED_ANTHURIUM, MAGIC_MUSHROOM, PATHOS, ACAI_VINE, CROTON, DRACAENA, FERN, FOLIAGE, BROMELIAD;
	
    @Override
    public String getName() {
    	return this.name().toLowerCase();
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
}
