package me.cooleg.paperexplosionresist;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.lang.reflect.Field;
import java.util.HashMap;

public class ModifyExplosionResistance {

    private static final HashMap<String, Block> getBlock = new HashMap<>();
    private static final HashMap<Block, Float> originalValues = new HashMap<>();
    private static Field resistanceField;

    static {
        try {
            for (Field field : Blocks.class.getFields()) {
                if (field.getType() != Block.class) {continue;}
                field.setAccessible(true);
                net.minecraft.world.level.block.Block block = (Block) field.get(null);
                getBlock.put(field.getName(), block);
            }
        } catch (IllegalAccessException ex) {}
        try {
            resistanceField = BlockBehaviour.class.getDeclaredField("explosionResistance");
            resistanceField.setAccessible(true);
        } catch (NoSuchFieldException ex) {
            System.out.println("Exception triggered :(");
        }
        if (resistanceField == null) {
            System.out.println("NO FIELD FOUND!");
        }
    }

    public static void setResistance(String s, float f) {
        if (!getBlock.containsKey(s.toUpperCase())) {return;}
        try {
            Block block = getBlock.get(s.toUpperCase());
            originalValues.put(block, resistanceField.getFloat(block));
            resistanceField.set(block, f);
        } catch (IllegalAccessException ex) {}
    }

    public static void resetResistances() {
        for (Block block : originalValues.keySet()) {
            try {
                resistanceField.set(block, originalValues.get(block));
            } catch (IllegalAccessException ex) {}
        }
    }

}
