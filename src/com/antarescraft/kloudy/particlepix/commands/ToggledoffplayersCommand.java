package com.antarescraft.kloudy.particlepix.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.antarescraft.kloudy.particlepix.ParticlePix;
import com.antarescraft.kloudy.particlepix.util.MessageManager;

public class ToggledoffplayersCommand implements Callable
{
	//particlepix toggledoffplayers [page]
	@Override
	public void call(CommandSender sender, ArrayList<String> args)
	{
		if(sender.hasPermission("pp.toggledisplay"))
		{
			ArrayList<String> playerNames = new ArrayList<String>();
			
			for(String playerName : ParticlePix.PlayerNoShow.values())
			{
				playerNames.add(playerName);
			}
			
			String title = "Display Toggled Off Players";
			
			if(args.size() > 0)
			{
				MessageManager.pageList(sender, playerNames, args.get(0), title);
			}
			else
			{
				MessageManager.pageList(sender, playerNames, title);
			}
		}
		else
		{
			MessageManager.error(sender, "You do not have permission to use this command");
		}
	}

	@Override
	public ArgsLength getArgsLength()
	{
		ArgsLength argsLength = ArgsLength.GREATER_THAN_OR_EQUAL_TO;
		argsLength.setNumArgs(0);
		return argsLength;
	}

	@Override
	public String commandUsage() 
	{
		return ChatColor.GOLD + "/particlepix toggledoffplayers [page]" + ChatColor.GRAY + "- " + ChatColor.GREEN + 
				"Displays a list of players who currently have display visibility toggled off";
	}
}
