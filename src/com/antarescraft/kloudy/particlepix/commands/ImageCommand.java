package com.antarescraft.kloudy.particlepix.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.antarescraft.kloudy.particlepix.util.MessageManager;
import com.antarescraft.kloudy.particlepix.ParticlePix;
import com.antarescraft.kloudy.particlepix.imageprocessing.ImageURL;

public class ImageCommand implements Callable
{
	//particlepix process <image_name> <url> [width] [height]
	@Override
	public void call(CommandSender sender, ArrayList<String> args)
	{
		if(sender.hasPermission("pp.image"))
		{
			if(args.size() >= 2)
			{
				if(!ParticlePix.imageExists(args.get(0)))
				{
					MessageManager.info(sender, "Processing image...");
					
					ImageURL imageUrl = new ImageURL(args.get(1), sender, args.get(0), ParticlePix.ParticleImages);
					imageUrl.runTaskAsynchronously(ParticlePix.plugin);
				}
				else
				{
					MessageManager.error(sender, "That image already exists. Choose another name.");
				}
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
		argsLength.setNumArgs(2);
		
		return argsLength;
	}

	@Override
	public String commandUsage()
	{
		return ChatColor.GOLD + "/particlepix image <image_name> <url> [width] [height]" + ChatColor.GRAY + " - " + ChatColor.GREEN + 
				"Processes and creates a new image from <url> with the given <image_name>. Optional width and height (cannot exceed config max)";
	}
}
