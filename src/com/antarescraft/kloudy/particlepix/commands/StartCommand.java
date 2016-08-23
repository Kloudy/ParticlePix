package com.antarescraft.kloudy.particlepix.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.antarescraft.kloudy.particlepix.ParticleDisplay;
import com.antarescraft.kloudy.particlepix.ParticleImage;
import com.antarescraft.kloudy.particlepix.ParticlePix;
import com.antarescraft.kloudy.particlepix.imageprocessing.ImageLauncher;
import com.antarescraft.kloudy.particlepix.util.MessageManager;

public class StartCommand implements Callable
{
	//particlepix start <image_name> <display_name>
	@Override
	public void call(CommandSender sender, ArrayList<String> args)
	{
		if(sender.hasPermission("pp.display"))
		{
			if(ParticlePix.imageExists(args.get(0)))
			{
				ParticleDisplay particleDisplay = ParticlePix.ParticleDisplays.get(args.get(1));
				
				if(particleDisplay != null)
				{
					ImageLauncher launcher = new ImageLauncher(sender, particleDisplay, args.get(0));
					
					ParticleImage particleImage = ParticlePix.ParticleImages.get(args.get(0));
					
					if(particleImage != null)//image already exists in memory
					{
						particleDisplay.setParticleImage(particleImage);
						ImageLauncher.start(particleDisplay);
					}
					else//retrieve image from disk
					{
						launcher.runTaskAsynchronously(ParticlePix.plugin);
					}
				}
				else
				{
					MessageManager.error(sender, "That display does not exist");
				}
			}
			else
			{
				MessageManager.error(sender, "That image does not exist");
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
		return ChatColor.GOLD + "/particlepix start <image_name> <display_name>" + ChatColor.GRAY + "- " + ChatColor.GREEN + 
				"Starts an image on the specifed display";
	}
}