package me.kw.set;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Note.Tone;
import org.bukkit.block.Block;
import org.bukkit.block.EnchantingTable;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Main extends JavaPlugin implements Listener {
	
	public static ConsoleCommandSender log = Bukkit.getConsoleSender();
	public static File runLog;
	public static PrintWriter writer;
	public static ArrayList<Event> eventsEncountered = new ArrayList<>();
	
	@Override
	public void onEnable() {
		
		log.sendMessage("§c[SpigotEventTester] Created by Kwright02 v" + getDescription().getVersion());
		
		Bukkit.getPluginManager().registerEvents(this, this);
		
		log.sendMessage("§c[SpigotEventTester] §2Hooked event listener");
		
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender.isOp() && command.getLabel().equalsIgnoreCase("scan")) {
			runLog = new File("event-log-" + System.currentTimeMillis() + ".txt");
			try {
				writer = new PrintWriter(runLog);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			RegisteredListener registeredListener = new RegisteredListener(this, (listener, event) -> onEvent(event), EventPriority.NORMAL, this, false);
	        for (HandlerList handler : HandlerList.getHandlerLists())
	            handler.register(registeredListener);
			sender.sendMessage("§eStarting scan on all spigot events..");
			//
			Player p = (Player) sender;
			
			Block cane = p.getLocation().add(3, 0, 3).getBlock();
			cane.getLocation().add(1, 0, 0).getBlock().setType(Material.WATER);
			cane.getLocation().add(0, 1, 0).getBlock().setType(Material.SUGAR_CANE);
			
			Block tnt = p.getLocation().add(10, 0, 10).getBlock();
			tnt.setType(Material.TNT);
			
			Block piston = p.getLocation().add(0, 20, 0).getBlock();
			piston.setType(Material.PISTON);
			
			Block leaves = p.getLocation().add(10, 10, 10).getBlock();
			leaves.setType(Material.DARK_OAK_LEAVES);
			
			Block ench = p.getLocation().add(30, 30, 30).getBlock();
			ench.setType(Material.ENCHANTING_TABLE);
			EnchantingTable enchant = (EnchantingTable) ench;
			
			
			Block build = p.getLocation().add(4, 0 , 8).getBlock();
			
			Block sign = p.getLocation().add(15, 0 , 15).getBlock();
			sign.setType(Material.OAK_SIGN);
			
			Block dispenser = p.getLocation().add(1, 1, 2).getBlock();
			dispenser.setType(Material.DISPENSER);
			Block below = p.getLocation().subtract(0, 1, 0).getBlock();
			Block above = p.getLocation().add(0, 4, 0).getBlock();
			
			Bukkit.getPluginManager().callEvent(new BlockBreakEvent(below, p));
			Bukkit.getPluginManager().callEvent(new BlockBurnEvent(below));
			Bukkit.getPluginManager().callEvent(new BlockCanBuildEvent(below, below.getBlockData(), true));
			Bukkit.getPluginManager().callEvent(new BlockDamageEvent(p, below, new ItemStack(Material.DIAMOND_PICKAXE), true));
			Bukkit.getPluginManager().callEvent(new BlockDispenseEvent(dispenser, new ItemStack(Material.DEAD_BUSH), new Vector(0, 0, 0)));
			Bukkit.getPluginManager().callEvent(new BlockExpEvent(below, 10));
			Bukkit.getPluginManager().callEvent(new BlockFadeEvent(below, below.getState()));
			Bukkit.getPluginManager().callEvent(new BlockFormEvent(below, below.getState()));
			Bukkit.getPluginManager().callEvent(new BlockFromToEvent(below, p.getLocation().add(0, 3, 0).getBlock()));
			Bukkit.getPluginManager().callEvent(new BlockGrowEvent(cane, cane.getState()));
			Bukkit.getPluginManager().callEvent(new BlockIgniteEvent(tnt, IgniteCause.FLINT_AND_STEEL, p));
			Bukkit.getPluginManager().callEvent(new BlockMultiPlaceEvent(Arrays.asList(build.getState()), build, new ItemStack(Material.COBBLESTONE), p, true));
			Bukkit.getPluginManager().callEvent(new BlockPhysicsEvent(below, below.getBlockData()));
			Bukkit.getPluginManager().callEvent(new BlockPistonExtendEvent(piston, 0, piston.getFace(below)));
			Bukkit.getPluginManager().callEvent(new BlockPistonRetractEvent(piston, null, piston.getFace(below)));
			Bukkit.getPluginManager().callEvent(new BlockPlaceEvent(above, above.getState(), above.getLocation().subtract(0, 1, 0).getBlock(), new ItemStack(Material.COBBLESTONE), p, true));
			Bukkit.getPluginManager().callEvent(new BlockRedstoneEvent(piston, 0, 0));
			Bukkit.getPluginManager().callEvent(new BlockSpreadEvent(build, build.getLocation().add(1,0,0).getBlock(), null));
			Bukkit.getPluginManager().callEvent(new EntityBlockFormEvent(p, above, above.getState()));
			Bukkit.getPluginManager().callEvent(new LeavesDecayEvent(leaves));
			Bukkit.getPluginManager().callEvent(new NotePlayEvent(above, Instrument.BANJO, Note.natural(0, Tone.A)));
			String[] stext = {"Hello","It","Worked"};
			Bukkit.getPluginManager().callEvent(new SignChangeEvent(sign, p, stext));
			//
			p.openEnchanting(enchant.getLocation(), true);
			Bukkit.getPluginManager().callEvent(new EnchantItemEvent(p, p.getOpenInventory(), ench, p.getItemInHand(), 0, p.getItemInHand().getEnchantments(), 0));
			//
			
			
			
		}
		return true;
	}
	
	@EventHandler
	public void onEvent(Event e) {
		if(eventsEncountered.contains(e)) return;
		writer.println("[" + e.getEventName() + "] Encountered at " + System.currentTimeMillis());
	}
	
	
	

}
