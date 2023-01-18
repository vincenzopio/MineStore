package it.vincenzopio.minestore.spigot.core.server.store.menu.utils;

import com.google.common.collect.Lists;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.function.Supplier;

public class ItemBuilder implements Supplier<ItemStack> {

    public static final ItemStack EMPTY_STACK = new ItemBuilder(Material.AIR).get();

    private final ItemStack itemStack;

    /**
     * Create a new ItemBuilder from scratch.
     *
     * @param material The material to create the ItemBuilder with.
     */
    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(Material material, Byte data) {
        itemStack = new ItemStack(material, 1, data);
    }

    /**
     * Create a new ItemBuilder over an existing itemstack.
     *
     * @param itemStack The itemstack to create the ItemBuilder over.
     */
    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Create a new ItemBuilder from scratch.
     *
     * @param material The material of the item.
     * @param amount   The amount of the item.
     */
    public ItemBuilder(Material material, int amount) {
        itemStack = new ItemStack(material, amount);
    }

    public ItemBuilder(Material material, short data) {
        itemStack = new ItemStack(material, 1, data);
    }

    public static ItemBuilder createItem(Material material) {
        return new ItemBuilder(material);
    }

    public static ItemBuilder createItem(Material material, Byte data) {
        return new ItemBuilder(material, data);

    }

    public static ItemBuilder createHead() {
        return new ItemBuilder(Material.SKULL_ITEM, (short) 3);
    }

    public ItemBuilder hideAttributes() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Sets the amount
     *
     * @param amount The amount
     */
    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    /**
     * Set the displayname of the item.
     *
     * @param name The name to change it to.
     */
    public ItemBuilder setName(String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(name);
        }
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Add an unsafe enchantment.
     *
     * @param enchantment The enchantment to add.
     * @param level       The level to put the enchant on.
     */
    public ItemBuilder addUnsafeEnchantment(Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    /**
     * Remove a certain enchant from the item.
     *
     * @param enchantment The enchantment to remove
     */
    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        itemStack.removeEnchantment(enchantment);
        return this;
    }

    /**
     * Set the skull owner for the item. Works on skulls only.
     *
     * @param owner The name of the skull's owner.
     */
    public ItemBuilder setSkullOwner(OfflinePlayer owner) {
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        if (skullMeta != null) {
            skullMeta.setOwner(owner.getName());
        }
        itemStack.setItemMeta(skullMeta);
        return this;
    }

    public ItemBuilder setSkullOwner(String owner) {
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        if (skullMeta != null) {
            skullMeta.setOwner(owner);
        }
        itemStack.setItemMeta(skullMeta);
        return this;
    }

    /**
     * Add an enchant to the item.
     *
     * @param enchantment The enchant to add
     * @param level       The level
     */
    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        if (level == 0) return this;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.addEnchant(enchantment, level, true);
            itemStack.setItemMeta(itemMeta);
        }
        return this;
    }

    public ItemBuilder addGlow() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Sets infinity durability on the item by setting the durability to Short.MAX_VALUE.
     */
    public ItemBuilder setUnbreakable() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.spigot().setUnbreakable(true);
        }
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Re-sets the lore.
     *
     * @param lore The lore to set it to.
     */
    public ItemBuilder setLore(String... lore) {
        return setLore(Lists.newArrayList(lore));
    }

    /**
     * Re-sets the lore.
     *
     * @param lore The lore to set it to.
     */
    public ItemBuilder setLore(List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        lore.add(0, "");
        if (itemMeta != null) {
            itemMeta.setLore(lore);
        }
        itemStack.setItemMeta(itemMeta);
        return this;
    }


    public ItemBuilder setColor(DyeColor dyeColor) {
        itemStack.setDurability(dyeColor.getWoolData());
        return this;
    }

    public ItemBuilder setColor(int red, int green, int blue) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        LeatherArmorMeta im = (LeatherArmorMeta) itemMeta;
        im.setColor(Color.fromRGB(red, green, blue));

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setColor(Color color) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        LeatherArmorMeta im = (LeatherArmorMeta) itemMeta;
        im.setColor(color);

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Retrieves the itemstack from the ItemBuilder.
     *
     * @return The itemstack created/modified by the ItemBuilder instance.
     */

    @Override
    public ItemStack get() {
        return itemStack;
    }
}