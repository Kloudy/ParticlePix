package com.antarescraft.kloudy.particlepix.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.antarescraft.kloudy.particlepix.ParticlePix;
import com.antarescraft.kloudy.particlepix.util.MessageManager;

public class HelpCommand implements Callable
{
	//particlepix help [page]
	@Override
	public void call(CommandSender sender, ArrayList<String> args) 
	{
		if(args.size() > 0)
		{
			MessageManager.pageList(sender, ParticlePix.CommandParser.CommandHelpStrings, args.get(0), "Help");
		}
		else
		{
			MessageManager.pageList(sender, ParticlePix.CommandParser.CommandHelpStrings, "Help");
		}
	}

	@Override
	public ArgsLength getArgsLength() {
		ArgsLength argsLength = ArgsLength.GREATER_THAN_OR_EQUAL_TO;
		argsLength.setNumArgs(0);
		
		return argsLength;
	}

	@Override
	public String commandUsage() {
		return ChatColor.GOLD + "/particlepix help" + ChatColor.GRAY + "- " + ChatColor.GREEN + 
				"Lists all of the plugin commands and what they do";
	}

}
