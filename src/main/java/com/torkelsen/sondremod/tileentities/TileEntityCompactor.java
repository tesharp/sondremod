/**
    Copyright (C) 2015 by jabelar

    This file is part of jabelar's Minecraft Forge modding examples; as such,
    you can redistribute it and/or modify it under the terms of the GNU
    General Public License as published by the Free Software Foundation,
    either version 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    For a copy of the GNU General Public License see <http://www.gnu.org/licenses/>.
*/

package com.torkelsen.sondremod.tileentities;

import com.torkelsen.sondremod.MainMod;
import com.torkelsen.sondremod.blocks.BlockCompactor;
import com.torkelsen.sondremod.containers.ContainerCompactor;
import com.torkelsen.sondremod.recipes.CompactorRecipes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.IItemHandler;

/**
 * @author jabelar
 *
 */
public class TileEntityCompactor extends TileEntityLockable implements ITickable, ISidedInventory
{
    // enumerate the slots
    public enum slotEnum 
    {
        INPUT_SLOT, OUTPUT_SLOT
    }
    protected static final int[] slotsTop = new int[] {slotEnum.INPUT_SLOT.ordinal()};
    protected static final int[] slotsBottom = new int[] {slotEnum.OUTPUT_SLOT.ordinal()};
    protected static final int[] slotsSides = new int[] {};
    
    /** The ItemStacks that hold the items currently being used in the compactor */
    protected NonNullList<ItemStack> compactorItemStacks = NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);
	
    protected boolean hasBeenCompacting = false;

    /** The number of ticks that the compactor will keep compacting */
    protected int timeCanCompact;
    protected int currentItemCompactTime; // not used currently but holdover from fuel-based tile entity
    protected int ticksCompactingItemSoFar;
    protected int ticksPerItem;
    
    protected String compactorCustomName;

    protected IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);
    protected IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.DOWN);
    protected IItemHandler handlerSide = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.WEST);


    /**
     * Returns the number of slots in the inventory.
     */
    @Override
	public int getSizeInventory()
    {
        return compactorItemStacks.size();
    }

	@Override
	public boolean isEmpty() 
	{
	    {
	        for (ItemStack itemstack : compactorItemStacks)
	        {
	            if (!itemstack.isEmpty())
	            {
	                return false;
	            }
	        }

	        return true;
	    }
	}

    /**
     * Returns the stack in slot i
     */
    @Override
	public ItemStack getStackInSlot(int index)
    {
        return compactorItemStacks.get(index);
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    @Override
	public ItemStack decrStackSize(int index, int count)
    {
        return ItemStackHelper.getAndSplit(this.compactorItemStacks, index, count);
    }

    /**
     * Removes a stack from the given slot and returns it.
     */
    @Override
	public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(this.compactorItemStacks, index);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    @Override
	public void setInventorySlotContents(int index, ItemStack stack)
    {
    	// DEBUG
    	System.out.println("TileEntityCompactor setInventorySlotContents()");
    	
        boolean isSameItemStackAlreadyInSlot = stack != ItemStack.EMPTY && stack.isItemEqual(compactorItemStacks.get(index)) && ItemStack.areItemStackTagsEqual(stack, compactorItemStacks.get(index));
        compactorItemStacks.set(index, stack);

        if (stack != ItemStack.EMPTY && stack.getCount() > getInventoryStackLimit())
        {
            stack.setCount(getInventoryStackLimit());
        }

        // if input slot, reset the compacting timers
        if (index == slotEnum.INPUT_SLOT.ordinal() && !isSameItemStackAlreadyInSlot)
        {
        	startCompacting();
        }
        
        markDirty();
    }
    
    protected void startCompacting()
    {
        ticksCompactingItemSoFar = 0;
        ticksPerItem = timeToCompactOneItem(compactorItemStacks.get(slotEnum.INPUT_SLOT.ordinal()));
    }

    /**
     * Gets the name of this command sender (usually username, but possibly "Rcon")
     */
    @Override
	public String getName()
    {
        return hasCustomName() ? compactorCustomName : "container.compactor";
    }

    /**
     * Returns true if this thing is named
     */
    @Override
	public boolean hasCustomName()
    {
        return compactorCustomName != null && compactorCustomName.length() > 0;
    }

    public void setCustomInventoryName(String parCustomName)
    {
        compactorCustomName = parCustomName;
    }

    @Override
	public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        compactorItemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, compactorItemStacks);

        timeCanCompact = compound.getShort("CompactTime");
        ticksCompactingItemSoFar = compound.getShort("CookTime");
        ticksPerItem = compound.getShort("CookTimeTotal");

        if (compound.hasKey("CustomName", 8))
        {
            compactorCustomName = compound.getString("CustomName");
        }
    }

    @Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setShort("CompactTime", (short)timeCanCompact);
        compound.setShort("CookTime", (short)ticksCompactingItemSoFar);
        compound.setShort("CookTimeTotal", (short)ticksPerItem);
        ItemStackHelper.saveAllItems(compound, compactorItemStacks);

        if (hasCustomName())
        {
            compound.setString("CustomName", compactorCustomName);
        }
        
        return compound;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    @Override
	public int getInventoryStackLimit()
    {
        return 64;
    }
    
    protected void compactingStateChanged(boolean parHasBeenCompacting)
    {
    	hasBeenCompacting = true;
		BlockCompactor.changeBlockBasedOnCompactingStatus(canCompact(), world, pos);
    }

    @Override
	public void update()
    {
//    	// DEBUG
//    	System.out.println("update() in TileEntityCompactor");

        if (!world.isRemote)
        {
        	// if something in input slot
            if (compactorItemStacks.get(slotEnum.INPUT_SLOT.ordinal()) != ItemStack.EMPTY)
            {      
            	// check if input is compactable
                if (canCompact())
                {
                	// check if just started compacting
                	if (!hasBeenCompacting)
                	{
    	            	// DEBUG
    	            	System.out.println("TileEntityCompactor update() started compacting");

    	            	compactingStateChanged(true);
    	            	startCompacting();       	
                	}
                	else // already compacting
                	{
		            	// DEBUG
		            	System.out.println("TileEntityCompactor update() continuing compacting");
		            	
	                    ++ticksCompactingItemSoFar;
	                    
	                    // check if completed compacting an item
	                    if (ticksCompactingItemSoFar >= ticksPerItem)
	                    {
	                    	// DEBUG
	                    	System.out.println("Compacting completed another output cycle");
	                    	
	                    	startCompacting();
	                        compactItem();
	                    }
                	}
                }
                else // item in input slot is not compactable
                {
                	if (hasBeenCompacting)
                	{
                		compactingStateChanged(false);
                	}
                	
                    ticksCompactingItemSoFar = 0;
                }
            }
            else // nothing in input slot
            {
            	if (hasBeenCompacting)
            	{
            		compactingStateChanged(false);
            	}
            	
                ticksCompactingItemSoFar = 0;
            }
        }
    }

    public int timeToCompactOneItem(ItemStack parItemStack)
    {
        return 200;
    }

    /**
     * Returns true if the compactor can compact an item, i.e. has a source item, destination stack isn't full, etc.
     */
    private boolean canCompact()
    {
    	ItemStack stackInOutputSlot = compactorItemStacks.get(slotEnum.OUTPUT_SLOT.ordinal());
    	ItemStack stackInInputSlot = compactorItemStacks.get(slotEnum.INPUT_SLOT.ordinal());
    			
    	// if nothing in input slot
        if (stackInInputSlot == ItemStack.EMPTY)
        {
            return false;
        }
        else // check if it has a compacting recipe
        {
        	// DEBUG
        	System.out.println("Checking if it has a valid compacting recipe");
            ItemStack itemStackToOutput = CompactorRecipes.instance().getCompactingResult(stackInInputSlot);
            if (itemStackToOutput == ItemStack.EMPTY) // no valid recipe for compacting this item
            {
            	// DEBUG
            	System.out.println("Does not have a valid compacting recipe");
            	return false;
            }
            if (stackInOutputSlot == ItemStack.EMPTY) // output slot is empty
            {
            	// check if enough of the input item (to allow recipes that consume multiple amounts)            }
            	if (stackInInputSlot.getCount() >= CompactorRecipes.instance().getInputAmount(stackInInputSlot))
            	{
//            		// DEBUG
//            		System.out.println("There is "+stackInInputSlot.stackSize+" in input slot and "+CompactorRecipes.instance().getInputAmount(stackInInputSlot)+" is needed");
            		return true;
            	}
            	else // not enough in input stack
            	{
//            		// DEBUG
//            		System.out.println("TileEntityCompactor canCompact() right item but not enough in input slot");
            		return false;
            	}
            }
            if (!stackInOutputSlot.isItemEqual(itemStackToOutput)) // output slot has different item occupying it
            {
            	return false;
            }
            // check if output slot is full
            int result = stackInOutputSlot.getCount() + itemStackToOutput.getCount();
            if (result <= getInventoryStackLimit() && result <= stackInOutputSlot.getMaxStackSize())
            {
            	// check if enough of the input item (to allow recipes that consume multiple amounts)            }
            	if (stackInInputSlot.getCount() >= CompactorRecipes.instance().getInputAmount(stackInInputSlot))
            	{
//            		// DEBUG
//            		System.out.println("There is "+stackInInputSlot.stackSize+" in input slot and "+CompactorRecipes.instance().getInputAmount(stackInInputSlot)+" is needed");
            		return true;
            	}
            	else // not enough in input stack
            	{
//            		// DEBUG
//            		System.out.println("TileEntityCompactor canCompact() right item but not enough in input slot");
            		return false;
            	}
            }
            else // no room to output
            {
            	return false;
            }
        }
    }

    /**
     * Turn one item from the compactor source stack into the appropriate compacted item in the compactor result stack
     */
    public void compactItem()
    {
        if (canCompact())
        {
            ItemStack itemstack = CompactorRecipes.instance().getCompactingResult(compactorItemStacks.get(slotEnum.INPUT_SLOT.ordinal()));

            // check if output slot is empty
            if (compactorItemStacks.get(slotEnum.OUTPUT_SLOT.ordinal()) == ItemStack.EMPTY)
            {
                compactorItemStacks.set(slotEnum.OUTPUT_SLOT.ordinal(), itemstack.copy());
            }
            else if (compactorItemStacks.get(slotEnum.OUTPUT_SLOT.ordinal()).getItem() == itemstack.getItem())
            {
                compactorItemStacks.get(slotEnum.OUTPUT_SLOT.ordinal()).setCount(compactorItemStacks.get(slotEnum.OUTPUT_SLOT.ordinal()).getCount() + itemstack.getCount()); // Forge BugFix: Results may have multiple items
            }

            // consume the number of input items based on recipe
            compactorItemStacks.get(slotEnum.INPUT_SLOT.ordinal()).setCount(
            		compactorItemStacks.get(slotEnum.INPUT_SLOT.ordinal()).getCount() 
            		- CompactorRecipes.instance().getInputAmount(compactorItemStacks.get(slotEnum.INPUT_SLOT.ordinal()))
            		);

            if (compactorItemStacks.get(slotEnum.INPUT_SLOT.ordinal()).getCount() <= 0)
            {
                compactorItemStacks.set(slotEnum.INPUT_SLOT.ordinal(), ItemStack.EMPTY);
            }
        }
    }

    @Override
	public void openInventory(EntityPlayer playerIn) {}

    @Override
	public void closeInventory(EntityPlayer playerIn) {}

    @Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return index == slotEnum.INPUT_SLOT.ordinal() ? true : false; // can always put things in input (may not compact though) and can't put anything in output
    }

    @Override
	public int[] getSlotsForFace(EnumFacing side)
    {
        return side == EnumFacing.DOWN ? slotsBottom : (side == EnumFacing.UP ? slotsTop : slotsSides);
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side. Args: slot, item,
     * side
     */
    @Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return isItemValidForSlot(index, itemStackIn);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side. Args: slot, item,
     * side
     */
    @Override
	public boolean canExtractItem(int parSlotIndex, ItemStack parStack, EnumFacing parFacing)
    {
        return true;
    }

    @Override
	public String getGuiID()
    {
        return MainMod.MODID+":compactor";
    }

    @Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
    	// DEBUG
    	System.out.println("TileEntityCompactor createContainer()");
        return new ContainerCompactor(playerInventory, this);
    }

    @Override
	public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return timeCanCompact;
            case 1:
                return currentItemCompactTime;
            case 2:
                return ticksCompactingItemSoFar;
            case 3:
                return ticksPerItem;
            default:
                return 0;
        }
    }

    @Override
	public void setField(int id, int value)
    {
        switch (id)
        {
            case 0:
                timeCanCompact = value;
                break;
            case 1:
                currentItemCompactTime = value;
                break;
            case 2:
                ticksCompactingItemSoFar = value;
                break;
            case 3:
                ticksPerItem = value;
                break;
		default:
			break;
        }
    }

    @Override
	public int getFieldCount()
    {
        return 4;
    }

    @Override
	public void clear()
    {
        compactorItemStacks.clear();
    }
    
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            if (facing == EnumFacing.DOWN)
                return (T) handlerBottom;
            else if (facing == EnumFacing.UP)
                return (T) handlerTop;
            else
                return (T) handlerSide;
        return super.getCapability(capability, facing);
    }

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) 
	{
		return true;
	}

}