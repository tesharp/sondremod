/**
 * 
 */
package com.torkelsen.sondremod.items;

import com.torkelsen.sondremod.utilities.Utilities;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;

/**
 * @author jabelar
 *
 */
public class ItemSwordExtended extends ItemSword implements IExtendedReach
{
	public ItemSwordExtended(ToolMaterial parMaterial) 
	{
		super(parMaterial);
		Utilities.setItemName(this, "swordExtended");
		setCreativeTab(CreativeTabs.COMBAT);
	}

	@Override
	public float getReach() 
	{
		return 30.0F;
	}

}
