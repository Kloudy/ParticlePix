package com.antarescraft.kloudy.particlepix.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.antarescraft.kloudy.particlepix.ParticlePix;

public class BaseCommand implements Callable
{
	@Override
	public void call(CommandSender sender, ArrayList<String> dataArguments)
	{
		String message = ChatColor.WHITE + "" + ChatColor.BOLD + "----------ParticlePix----------\n";
		message += ChatColor.GOLD + "Author: " + ChatColor.RED + "Kloudy\n";
		message += ChatColor.GOLD + "Version: " + ChatColor.AQUA + ParticlePix.PluginDescription.getVersion() + "\n";
		message += ChatColor.GRAY + "playminecraft.net\n";
		message += ChatColor.WHITE + "" + ChatColor.BOLD + "-----------------------------";

		sender.sendMessage(message);
	}
	
	@Override
	public ArgsLength getArgsLength()
	{
		ArgsLength argsLength = ArgsLength.EQUALS;
		argsLength.setNumArgs(0);
		
		return argsLength;
	}
	
	@Override
	public String commandUsage()
	{
		return ChatColor.GOLD + "/particlepix " + ChatColor.GRAY + "- " + ChatColor.GREEN + 
				"Display author, version, and website";
	}
}
