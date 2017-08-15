/**
    Copyright (C) 2014 by jabelar

    This file is part of jabelar's Minecraft Forge modding examples; as such,
    you can redistribute it and/or modify it under the terms of the GNU
    General Public License as published by the Free Software Foundation,
    either version 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    For a copy of the GNU General Public License see <http://www.gnu.org/licenses/>.

	If you're interested in licensing the code under different terms you can
	contact the author at julian_abelar@hotmail.com 
*/

package com.torkelsen.sondremod;

import com.torkelsen.sondremod.blocks.BlockSondreMod;
import com.torkelsen.sondremod.gui.GuiCompactor;
import com.torkelsen.sondremod.registries.ItemRegistry;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.NameFormat;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventHandler 
{
	/*
	 * Registry events
	 */
	
  @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
  public void onEvent(RegistryEvent.NewRegistry event)
  {
	  // can create registries here if needed
  }
	
    /*
     * Miscellaneous events
     */    

//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(ForceChunkEvent event)
//    {
//        
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(UnforceChunkEvent event)
//    {
//        
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(AnvilUpdateEvent event)
//    {
//        
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(CommandEvent event)
//    {
//        
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(ServerChatEvent event)
//    {
//        
//    }
//    
//    /*
//     * Brewing events
//     */
//        
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(PotionBrewedEvent event)
//    {
//        
//    }
//    
//    /*
//     * Entity related events
//     */
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(EnteringChunk event)
//    {
//        
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(EntityConstructing event)
//    {
//        // Register extended entity properties
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(EntityJoinWorldEvent event)
//    {
//
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(EntityStruckByLightningEvent event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(PlaySoundAtEntityEvent event)
//    {
//        
//    }
//
//    /*
//     * Item events (these extend EntityEvent)
//     */
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(ItemExpireEvent event)
//    {
//        
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(ItemTossEvent event)
//    {
//        
//    }
//    
//    /*
//     * Living events (extend EntityEvent)
//     */
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(LivingJumpEvent event)
//    {
//
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(LivingUpdateEvent event)
//    {
//
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(EnderTeleportEvent event)
//    {
//        
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(LivingAttackEvent event)
//    {
//
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(LivingDeathEvent event)
//    {
//        
//    }

    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void onEvent(LivingDropsEvent event)
    {
		for (EntityItem dropItem: event.getDrops())
		{
			if (dropItem.getItem().getItem() == Items.LEATHER)
			{
				int stackSize = dropItem.getItem().getCount();

				if (event.getEntityLiving() instanceof EntityCow)
				{
					dropItem.setItem(new ItemStack(ItemRegistry.COW_HIDE, stackSize));
				}
				if (event.getEntityLiving() instanceof EntityHorse)
				{
					dropItem.setItem(new ItemStack(ItemRegistry.HORSE_HIDE, stackSize));
				}
				if (event.getEntityLiving() instanceof EntityMooshroom)
				{
					dropItem.setItem(new ItemStack(ItemRegistry.COW_HIDE, stackSize));
				}
			}
    	}
    	
		if (event.getEntityLiving() instanceof EntityPig)
		{
			event.getDrops().add(new EntityItem(
			        event.getEntityLiving().world, 
			        event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, 
					new ItemStack(ItemRegistry.PIG_SKIN)));
		}
		else if (event.getEntityLiving() instanceof EntitySheep)
		{
			event.getDrops().add(new EntityItem(
			        event.getEntityLiving().world, 
			        event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, 
					new ItemStack(ItemRegistry.SHEEP_SKIN)));
		}
    }
    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(LivingFallEvent event)
//    {
//
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(LivingHurtEvent event)
//    {
//
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(LivingPackSizeEvent event)
//    {
//        
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(LivingSetAttackTargetEvent event)
//    {
//        
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(ZombieEvent event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(CheckSpawn event)
//    {  	
//
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(SpecialSpawn event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(AllowDespawn event)
//    {
//        
//    }
//    
//    /*
//     * Player events (extend LivingEvent)
//     */
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(BreakSpeed event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(Clone event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(HarvestCheck event)
//    {
//        
//    }
    
    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void onEvent(NameFormat event)
    {
    	// DEBUG
    	System.out.println("NameFormat event for username = "+event.getUsername());
        if (event.getUsername().equalsIgnoreCase("jnaejnae"))
        {
            event.setDisplayname(event.getUsername()+" the Great and Powerful");
        }        
        else if (event.getUsername().equalsIgnoreCase("MistMaestro"))
        {
            event.setDisplayname(event.getUsername()+" the Wise");
        }    
        else if (event.getUsername().equalsIgnoreCase("Taliaailat"))
        {
            event.setDisplayname(event.getUsername()+" the Beautiful");
        }    
        else
        {
            event.setDisplayname(event.getUsername()+" the Ugly");            
        }
    }
    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(ArrowLooseEvent event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(ArrowNockEvent event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(AttackEntityEvent event)
//    {
//
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(BonemealEvent event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(EntityInteractEvent event)
//    {
//
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(EntityItemPickupEvent event)
//    {
//
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(FillBucketEvent event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(ItemTooltipEvent event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(PlayerDestroyItemEvent event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(PlayerDropsEvent event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(PlayerFlyableFallEvent event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(PlayerInteractEvent event)
//    {
//
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(PlayerOpenContainerEvent event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(PlayerPickupXpEvent event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(PlayerSleepInBedEvent event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(PlayerUseItemEvent.Finish event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(PlayerUseItemEvent.Start event)
//    {
//
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(PlayerUseItemEvent.Stop event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(PlayerUseItemEvent.Tick event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(UseHoeEvent event)
//    {
//        
//    }
//    
//    /*
//     * Minecart events (extends EntityEvent)
//     */
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(MinecartCollisionEvent event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(MinecartInteractEvent event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(MinecartUpdateEvent event)
//    {
//        
//    }
//    
//    /*
//     * World events
//     */
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(WorldEvent.Load event)
//    {
//        
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(WorldEvent.PotentialSpawns event)
//    {
//        
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(WorldEvent.Unload event)
//    {
//        
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(BlockEvent event)
//    {
//        
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(BlockEvent.BreakEvent event)
//    {
//        
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(BlockEvent.HarvestDropsEvent event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(ChunkEvent event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(ChunkEvent.Save event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(ChunkEvent.Unload event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(ChunkDataEvent event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(ChunkDataEvent.Load event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(ChunkDataEvent.Save event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(ChunkWatchEvent event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(ChunkWatchEvent.Watch event)
//    {
//        
//    }
//    
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(ChunkWatchEvent.UnWatch event)
//    {
//        
//    }
//
//
//    /*
//     * Client events
//     */    
//
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(ClientChatReceivedEvent event)
//    {
//        
//    }
//
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(DrawBlockHighlightEvent event)
//    {
//        
//    }
//
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(RenderFogEvent event)
//    {
//        
//    }
//    
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(FogDensity event)
//    {
//
//    }
//
//    
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(FogColors event)
//    {
//
//    }
//
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(FOVUpdateEvent event)
//    {
//        
//    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void onEvent(GuiOpenEvent event)
    { 
    	if (event.getGui() instanceof GuiCompactor)
    	{
    		// DEBUG
    		System.out.println("GuiOpenEvent for GuiCompactor");
    	}
//        if (event.getGui() instanceof GuiIngameMenu)
//        {
//            System.out.println("GuiOpenEvent for GuiIngameModOptions");
//            event.setGui(new GuiConfig(null));        
//        }
    }

//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(GuiScreenEvent.ActionPerformedEvent event)
//    {
//        
//    }
//
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(GuiScreenEvent.DrawScreenEvent event)
//    {
//        
//    }
//
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(GuiScreenEvent.InitGuiEvent event)
//    {
//        
//    }

//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(MouseEvent event)
//    { 
//        if (event.getButton() == 0 && event.isButtonstate())
//        {
//            Minecraft mc = Minecraft.getMinecraft();
//            EntityPlayer thePlayer = mc.player;
//            if (thePlayer != null)
//            {
//                ItemStack itemstack = thePlayer.getHeldItemMainhand();
//                IExtendedReach ieri;
//                if (itemstack != null)
//                {
//                    if (itemstack.getItem() instanceof IExtendedReach)
//                    {
//                        ieri = (IExtendedReach) itemstack.getItem();
//                    } else
//                    {
//                        ieri = null;
//                    }
//    
//                    if (ieri != null)
//                    {
//                        float reach = ieri.getReach();
//                        RayTraceResult mov = Utilities.getMouseOverExtended(reach); 
//                        
//                        if (mov != null)
//                        {
//                            if (mov.entityHit != null && mov.entityHit.hurtResistantTime == 0)
//                            {
//                                if (mov.entityHit != thePlayer )
//                                {
//                                    MainMod.network.sendToServer(new MessageExtendedReachAttack(mov.entityHit.getEntityId()));
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//   }

//    
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(RenderGameOverlayEvent event)
//    {
//        
//    }
//    
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(RenderGameOverlayEvent.Chat event)
//    {
//    	// This event actually extends Pre
//
//    }
//    
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(RenderGameOverlayEvent.Post event)
//    {
//        
//    }
//    
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(RenderGameOverlayEvent.Pre event)
//    {
//    	// you can check which elements of the GUI are being rendered
//    	// by checking event.type against things like ElementType.CHAT, ElementType.CROSSHAIRS, etc.
//    	// Note that ElementType.All is fired first apparently, then individual elements
//    }
//    
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(RenderGameOverlayEvent.Text event)
//    {
//    	// This event actually extends Pre
//        
//    }
//
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(RenderHandEvent event)
//    {
//        
//    }
//
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(RenderLivingEvent.Post event)
//    {
//        
//    }
//
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(RenderLivingEvent.Pre event)
//    {
//        
//    }
//
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(RenderPlayerEvent.Post event)
//    {
//        
//    }
//
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(RenderPlayerEvent.Pre event)
//    {
//
//    }
//
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(RenderPlayerEvent.SetArmorModel event)
//    {
//        
//    }
//
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(RenderWorldEvent.Post event)
//    {
//        
//    }
//
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(RenderWorldEvent.Pre event)
//    {
//    	Minecraft.getMinecraft().gameSettings.thirdPersonView = 2;
//    }
//
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(RenderWorldLastEvent event)
//    {
//        
//    }
//
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(TextureStitchEvent.Post event)
//    {
//        
//    }
//
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(TextureStitchEvent.Pre event)
//    {
//        
//    }
//    
//    /*
//     * Fluid events
//     */
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(FluidEvent event)
//    {
//        
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(FluidContainerRegisterEvent event)
//    {
//        
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(FluidDrainingEvent event)
//    {
//        
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(FluidFillingEvent event)
//    {
//        
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(FluidMotionEvent event)
//    {
//        
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(FluidRegisterEvent event)
//    {
//        
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(FluidSpilledEvent event)
//    {
//        
//    }
//
//    /*
//     * Ore dictionary events
//     */
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(OreRegisterEvent event)
//    {
//        
//    }
//    
//	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//	public void onEvent(PopulateChunkEvent event)
//	{
//		
//	}
//	
//	// for some reason the PopulateChunkEvents are fired on the main EVENT_BUT
//	// even though they are in the terraingen package
//	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//	public void onEvent(PopulateChunkEvent.Populate event)
//	{
//		
//	}
//	
//	// for some reason the PopulateChunkEvents are fired on the main EVENT_BUT
//	// even though they are in the terraingen package
//	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//	public void onEvent(PopulateChunkEvent.Post event)
//	{ 
//		
//	}
//	
//	// for some reason the PopulateChunkEvents are fired on the main EVENT_BUT
//	// even though they are in the terraingen package
//	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//	public void onEvent(PopulateChunkEvent.Pre event)
//	{
//		
//	}
    /*
     * Common events
     */

    // events in the cpw.mods.fml.common.event package are actually handled with
    // @EventHandler annotation in the main mod class or the proxies.
    
    /*
     * Game input events
     */

//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(InputEvent event)
//    {
//        
//    }
//
//    @SideOnly(Side.CLIENT)
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(KeyInputEvent event)
//    {
//
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(MouseInputEvent event)
//    {
//
//    }
//    
//    /*
//     * Player events
//     */
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(PlayerEvent event)
//    {
//        
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(ItemCraftedEvent event)
//    {
//        
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(ItemPickupEvent event)
//    {
//        
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(ItemSmeltedEvent event)
//    {
//        
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(PlayerChangedDimensionEvent event)
//    {
//        
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(PlayerLoggedInEvent event)
//    {
//        
//    }

    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void onEvent(PlayerLoggedOutEvent event)
    {
        // DEBUG
        System.out.println("Player logged out");
        
    }

//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(PlayerRespawnEvent event)
//    {
//        // DEBUG
//        System.out.println("The memories of past existences are but glints of light.");
//        
//    }
//
//    /*
//     * Tick events
//     */
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(ClientTickEvent event) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
//    {
//        if (event.phase == TickEvent.Phase.END) // only proceed if START phase otherwise, will execute twice per tick
//        {
//            return;
//        }    
//
//    }

//    boolean haveRequestedItemStackRegistry = false;
//    boolean haveGivenGift = false;
            
    @SuppressWarnings("deprecation")
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void onEvent(PlayerTickEvent event)
    {        
        if (event.phase == TickEvent.Phase.START && event.player.world.isRemote) // only proceed if START phase otherwise, will execute twice per tick
        {
            EntityPlayer thePlayer = event.player;
            if (!MainMod.haveWarnedVersionOutOfDate && !MainMod.versionChecker.isLatestVersion())
            {
                ClickEvent versionCheckChatClickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, "http://jabelarminecraft.blogspot.com");
                Style clickableStyle = new Style().setClickEvent(versionCheckChatClickEvent);
                TextComponentString versionWarningChatComponent = new TextComponentString("Your Magic Beans Mod is not latest version!  Click here to update.");
                versionWarningChatComponent.setStyle(clickableStyle);
                thePlayer.sendMessage(versionWarningChatComponent);
                MainMod.haveWarnedVersionOutOfDate = true;
            }
        }
        else if (event.phase == TickEvent.Phase.START && !event.player.world.isRemote)
        {
         	// check if player is holding a light source
            if (BlockSondreMod.isHoldingLightItem(event.player))
            {
//                	// DEBUG
//                	System.out.println("Holding torch");
            	
            	// determine player position
                int blockX = MathHelper.floor(event.player.posX);
                int blockY = MathHelper.floor(event.player.posY-0.2D - event.player.getYOffset());
                int blockZ = MathHelper.floor(event.player.posZ);
                
                // place light where there is space to do so
                BlockPos blockLocation = new BlockPos(blockX, blockY, blockZ).up();
                Block blockAtLocation = event.player.world.getBlockState(blockLocation).getBlock();
//                    // DEBUG
//                    System.out.println("Block at player position is "+event.player.world.getBlockState(blockLocation).getBlock());
                if (blockAtLocation == Blocks.AIR)
                {
//                    	// DEBUG
//                    	System.out.println("There is space at player location "+blockLocation+" to place block");
                	
                	// there is space to create moving light source block
                    event.player.world.setBlockState(
                    		blockLocation, 
                    		BlockSondreMod.lightBlockToPlace(event.player).getDefaultState()
                    		);
                }
                else if (blockAtLocation instanceof BlockSondreMod)
                {
//                	// DEBUG
//                	System.out.println("There is already a BlockMovingLight at player location "+blockLocation);
                	// check if light value at location should change (due to change in held item)
                	if (blockAtLocation.getLightValue(blockAtLocation.getDefaultState()) != 
                			BlockSondreMod.lightBlockToPlace(event.player).getLightValue(BlockSondreMod.lightBlockToPlace(event.player).getDefaultState()))
            	// there is space to create moving light source block
                event.player.world.setBlockState(
                		blockLocation, 
                		BlockSondreMod.lightBlockToPlace(event.player).getDefaultState()
                		);
                }

//                if (event.player.world.getBlockState(blockLocation.add(event.player.getLookVec().x, event.player.getLookVec().y, event.player.getLookVec().y)).getBlock() == Blocks.AIR)
//                {
////                    	// DEBUG
////                    	System.out.println("There is space in front of player to place block");
//                	
//                    event.player.world.setBlockState(blockLocation.add(
//                    		event.player.getLookVec().x, 
//                    		event.player.getLookVec().y, 
//                    		event.player.getLookVec().y), 
//                    		BlockSondreMod.lightBlockToPlace(event.player).getDefaultState()
//                    		);
//                }
            }
        }
    }

//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(RenderTickEvent event)
//    {
//        if (event.phase == TickEvent.Phase.END) // only proceed if START phase otherwise, will execute twice per tick
//        {
//            return;
//        }
//        
//    }
//
//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(ServerTickEvent event)
//    {
//        if (event.phase == TickEvent.Phase.END) // only proceed if START phase otherwise, will execute twice per tick
//        {
//            return;
//        }    
//        
//    }

//  @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//  public void onEvent(WorldTickEvent event)
//  {
//      if (event.phase == TickEvent.Phase.END) // only proceed if START phase otherwise, will execute twice per tick
//      {
//          return;
//      }          
//  }

    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void onEvent(OnConfigChangedEvent eventArgs) 
    {
        // DEBUG
        System.out.println("OnConfigChangedEvent");
        if(eventArgs.getModID().equals(MainMod.MODID))
        {
            System.out.println("Syncing config for mod ="+eventArgs.getModID());
            MainMod.config.save();
            MainMod.proxy.syncConfig();
        }
    }

//    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//    public void onEvent(PostConfigChangedEvent eventArgs) 
//    {
//        // useful for doing something if another mod's config has changed
//        // if(eventArgs.modID.equals(MagicBeans.MODID))
//        // {
//        //        // do whatever here
//        // }
//    }
}


