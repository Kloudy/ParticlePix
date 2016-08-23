package com.antarescraft.kloudy.particlepix.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.antarescraft.kloudy.particlepix.ParticleDisplay;
import com.antarescraft.kloudy.particlepix.ParticlePix;
import com.antarescraft.kloudy.particlepix.util.MessageManager;

public class DisplaylistCommand implements Callable
{
	//particlepix displaylist [page]
	@Override
	public void call(CommandSender sender, ArrayList<String> args)
	{
		if(sender.hasPermission("pp.display"))
		{
			ArrayList<String> displayNames = new ArrayList<String>();
			
			for(ParticleDisplay particleDisplay : ParticlePix.ParticleDisplays.values())
			{
				displayNames.add(particleDisplay.getName());
			}
			
			if(args.size() > 0)
			{
				MessageManager.pageList(sender, displayNames, args.get(0), "Displays");
			}
			else
			{
				MessageManager.pageList(sender, displayNames, "Displays");
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
		return ChatColor.GOLD + "/particlepix displaylist" + ChatColor.GRAY + "- " + ChatColor.GREEN + 
				"Shows a list of created particle displays";
	}

}
