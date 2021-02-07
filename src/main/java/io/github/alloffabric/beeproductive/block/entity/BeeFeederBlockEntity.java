package io.github.alloffabric.beeproductive.block.entity;

import io.github.alloffabric.beeproductive.init.BeeProdBlockEntities;
import io.github.alloffabric.beeproductive.init.BeeProdBlocks;
import io.github.alloffabric.beeproductive.item.NectarItem;
import io.github.alloffabric.beeproductive.util.ImplementedInventory;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;

import javax.annotation.Nullable;

public class BeeFeederBlockEntity extends BlockEntity implements ImplementedInventory, SidedInventory, BlockEntityClientSerializable {
	public BeeFeederBlockEntity() {
		super(BeeProdBlockEntities.FEEDER_ENTITY);
	}
	private DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);

	@Override
	public DefaultedList<ItemStack> getItems() {
		return items;
	}

	@Override
	public void markDirty() {
		sync();
	}

	@Override
	public void fromTag(BlockState state, CompoundTag tag) {
		super.fromTag(state, tag);
		Inventories.fromTag(tag.getCompound("Inventory"), items);
	}

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		tag.put("Inventory", Inventories.toTag(new CompoundTag(), items));
		return super.toTag(tag);
	}

	@Override
	public void fromClientTag(CompoundTag tag) {
		fromTag(BeeProdBlocks.BEE_FEEDER.getDefaultState(), tag);
	}

	@Override
	public CompoundTag toClientTag(CompoundTag tag) {
		return toTag(tag);
	}

	@Override
	public int[] getAvailableSlots(Direction side) {
		// Just return an array of all slots
		int[] result = new int[getItems().size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = i;
		}

		return result;
	}

	@Override
	public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
		return stack.getItem() instanceof NectarItem;
	}

	@Override
	public boolean canExtract(int slot, ItemStack stack, Direction dir) {
		return true;
	}
}
