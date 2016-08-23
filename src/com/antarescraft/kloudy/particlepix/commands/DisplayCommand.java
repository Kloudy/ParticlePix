package com.antarescraft.kloudy.particlepix.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.antarescraft.kloudy.particlepix.ParticleDisplay;
import com.antarescraft.kloudy.particlepix.ParticlePix;
import com.antarescraft.kloudy.particlepix.util.IOManager;
import com.antarescraft.kloudy.particlepix.util.MessageManager;

public class DisplayCommand implements Callable
{
	//particlepix display <display_name> [north|south|east|west]
	@Override
	public void call(CommandSender sender, ArrayList<String> args) 
	{
		if(sender.hasPermission("pp.display"))
		{
			if(!ParticlePix.ParticleDisplays.containsKey(args.get(0)))
			{
				if(sender instanceof Player)
				{
					Player player = (Player)sender;
					
					ParticleDisplay display = new ParticleDisplay(args.get(0), player.getLocation());
					ParticlePix.ParticleDisplays.put(args.get(0), display);
					
					IOManager.saveDisplays();
					
					MessageManager.success(sender, "Successfully created the display");
				}
				else
				{
					MessageManager.error(sender, "You must be a player to run this command");
				}
			}
			else
			{
				MessageManager.error(sender, "That particle display already exists. Choose another name.");
			}
		}
		else
		{
			MessageManager.error(sender, "You do not have permission to use this command");
		}
	}

	@Override
	public ArgsLength getArgsLength() {
		ArgsLength argsLength = ArgsLength.GREATER_THAN_OR_EQUAL_TO;
		argsLength.setNumArgs(1);
		
		return argsLength;
	}

	@Override
	public String commandUsage() {
		return ChatColor.GOLD + "/particlepix display <display_name> [north|south|east|west]" + ChatColor.GRAY + "- " + ChatColor.GREEN + 
				"Creates a display where the player is standing. If no direction is specified, then the display faces opposite way the player is facing.";
	}
	
}
