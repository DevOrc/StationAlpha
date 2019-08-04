package com.noahcharlton.stationalpha.item;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.world.Inventory;

import java.util.Objects;

public class ItemStack {

    private final Item item;
    private final int amount;

    private ItemStack(Item item, int amount) {
        this.item = Objects.requireNonNull(item);
        this.amount = amount;

        if(amount < 0){
            throw new GdxRuntimeException("Cannot create stack of negative size!");
        }
    }

    public static ItemStack of(Item item){
        return of(item, 1);
    }

    public static ItemStack of(Item item, int amount){
        return new ItemStack(item, amount);
    }

    public boolean resourcesAvailable(Inventory inventory){
        return inventory.getAmountForItem(item) >= amount;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof ItemStack)) return false;
        ItemStack itemStack = (ItemStack) o;
        return amount == itemStack.amount &&
                item == itemStack.item;
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, amount);
    }

    @Override
    public String toString() {
        return "ItemStack{" +
                "item=" + item +
                ", amount=" + amount +
                '}';
    }
}
