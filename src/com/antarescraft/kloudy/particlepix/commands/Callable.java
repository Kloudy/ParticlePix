package com.antarescraft.kloudy.particlepix.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

public interface Callable 
{
	public void call(CommandSender sender, ArrayList<String> args);
	public ArgsLength getArgsLength();
	public String commandUsage();
}
