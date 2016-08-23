package com.antarescraft.kloudy.particlepix.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import com.antarescraft.kloudy.particlepix.FramePlayer;
import com.antarescraft.kloudy.particlepix.ParticleDisplay;
import com.antarescraft.kloudy.particlepix.ParticlePix;
import com.antarescraft.kloudy.particlepix.util.IOManager;
import com.antarescraft.kloudy.particlepix.util.MessageManager;

public class DeletedisplayCommand implements Callable
{
	//particlepix deletedisplay <display_name>
	@Override
	public void call(final CommandSender sender, ArrayList<String> args) 
	{
		if(sender.hasPermission("pp.display"))
		{
			ParticleDisplay particleDisplay = ParticlePix.ParticleDisplays.get(args.get(0));
			
			if(particleDisplay != null)
			{
				FramePlayer framePlayer = ParticlePix.FramePlayers.get(particleDisplay.getName());
				
				if(framePlayer != null)
				{
					framePlayer.cancel();
					ParticlePix.FramePlayers.remove(particleDisplay.getName());
				}
				
				ParticlePix.ParticleDisplays.remove(particleDisplay.getName());
				
				BukkitRunnable asyncTask = new BukkitRunnable()
				{
					@Override
					public void run()
					{
						IOManager.saveDisplays();
						
						MessageManager.success(sender, "Successfully removed the display");
					}
				};
				asyncTask.runTaskAsynchronously(ParticlePix.plugin);
			}
			else
			{
				MessageManager.error(sender, "That display does not exist");
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
		ArgsLength argsLength = ArgsLength.EQUALS;
		argsLength.setNumArgs(1);
		
		return argsLength;
	}

	@Override
	public String commandUsage()
	{
		return ChatColor.GOLD + "/particlepix deletedisplay <display_name>" + ChatColor.GRAY + "- " + ChatColor.GREEN + 
				"Deletes the specified particle display if it exists";
	}

}
