package com.antarescraft.kloudy.particlepix.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.antarescraft.kloudy.particlepix.ParticlePix;
import com.antarescraft.kloudy.particlepix.util.MessageManager;

public class ToggledisplayCommand implements Callable
{
	//particlepix displaytoggle [playername]
	@Override
	public void call(CommandSender sender, ArrayList<String> args) 
	{
		if(sender.hasPermission("pp.toggledisplay"))
		{
			if(args.size() > 0)
			{
				Player player = Bukkit.getPlayer(args.get(0));
				
				if(player != null)
				{
					toggle(player, player);
					
					MessageManager.success(sender, "Toggled ParticlePix visibility for " + player.getName());
				}
				else
				{
					MessageManager.error(sender, "Could not find that player");
				}
			}
			else
			{
				if(sender instanceof Player)
				{
					Player player = (Player)sender;
					
					toggle(sender, player);
				}
				else
				{
					MessageManager.error(sender, "You must be a player to do that");
				}
			}
		}
		else
		{
			MessageManager.error(sender, "You do not have permission to use this command");
		}
	}
	
	private void toggle(CommandSender sender, Player player)
	{
		if(ParticlePix.PlayerNoShow.containsKey(player.getUniqueId()))
		{
			ParticlePix.PlayerNoShow.remove(player.getUniqueId());
			
			MessageManager.success(sender, "You will now see the particle displays");
		}
		else
		{
			ParticlePix.PlayerNoShow.put(player.getUniqueId(), player.getName());
			
			MessageManager.success(sender, "You will no longer see the particle displays");
		}
	}

	@Override
	public ArgsLength getArgsLength() {
		ArgsLength argsLength = ArgsLength.GREATER_THAN_OR_EQUAL_TO;
		argsLength.setNumArgs(0);
		
		return argsLength;
	}

	@Override
	public String commandUsage() 
	{
		return ChatColor.GOLD + "/particlepix toggledisplay [player_name]" + ChatColor.GRAY + "- " + ChatColor.GREEN + 
				"Toggles the particle visibilty for a player.";
	}
}
