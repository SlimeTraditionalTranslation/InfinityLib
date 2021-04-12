package io.github.mooy1.infinitylib.slimefun.presets;

import lombok.experimental.UtilityClass;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

/**
 * Collection of utils for creating BlockMenuPresets
 *
 * @author Mooy1
 * 
 */
@UtilityClass
public final class MenuPreset {
    
    public static final int[] slotChunk1 = {0, 1, 2, 9, 11, 18, 19, 20};
    public static final int slot1 = 10;

    public static final int[] slotChunk2 = {3, 4, 5, 12, 14, 21, 22, 23};
    public static final int slot2 = 13;

    public static final int[] slotChunk3 = {6, 7, 8, 15, 17, 24, 25, 26};
    public static final int slot3 = 16;
    
    public static final int[] craftingInputBorder = {
            0, 1, 2, 3, 4,
            9, 13,
            18, 22,
            27, 31,
            36, 37, 38, 39, 40
    };

    public static final int[] craftingInput = {
            10, 11, 12,
            19, 20, 21,
            28, 29, 30
    };
    public static final int[] craftingBackground = {
            5, 6, 7, 14, 8, 23,
            41, 42, 43, 44, 32
    };

    public static final int[] craftingOutput = {25};
    public static final int[] craftingOutputBorder = {24, 26, 15, 16, 17, 33, 34, 35};
    
    public static void setupBasicMenu(BlockMenuPreset preset) {
        for (int i : slotChunk1) {
            preset.addItem(i, borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : slotChunk2) {
            preset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : slotChunk3) {
            preset.addItem(i, borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
    }
    
    public static final ItemStack invalidInput = (new CustomItem(
            Material.BARRIER, "&c無效輸入!")
    );
    public static final ItemStack inputAnItem = (new CustomItem(
            Material.BLUE_STAINED_GLASS_PANE, "&9輸入物品")
    );
    public static final ItemStack invalidRecipe = (new CustomItem(
            Material.BARRIER, "&c無效配方!")
    );
    public static final ItemStack notEnoughEnergy = (new CustomItem(
            Material.RED_STAINED_GLASS_PANE, "&c能源不足!")
    );
    public static final ItemStack notEnoughRoom = (new CustomItem(
            Material.ORANGE_STAINED_GLASS_PANE, "&6空間不足!")
    );
    public static final ItemStack borderItemInput = (new CustomItem(
            Material.BLUE_STAINED_GLASS_PANE, "&9輸入")
    );
    public static final ItemStack borderItemOutput = (new CustomItem(
            Material.ORANGE_STAINED_GLASS_PANE, "&6輸出")
    );
    public static final ItemStack borderItemStatus = (new CustomItem(
            Material.CYAN_STAINED_GLASS_PANE, "&3狀態")
    );
    public static final ItemStack connectToEnergyNet = (new CustomItem(
            Material.RED_STAINED_GLASS_PANE, "&c連接至一個能源網路!")
    );
    public static final ItemStack loadingItemRed = (new CustomItem(
            Material.RED_STAINED_GLASS_PANE, "&c載入中...")
    );
    
}
