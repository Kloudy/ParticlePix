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

public class DeletealldisplaysCommand implements Callable
{
	//particlepix deletealldisplays
	@Override
	public void call(final CommandSender sender, ArrayList<String> args) 
	{
		if(sender.hasPermission("pp.display"))
		{
			for(ParticleDisplay particleDisplay : ParticlePix.ParticleDisplays.values())
			{
				FramePlayer framePlayer = ParticlePix.FramePlayers.get(particleDisplay.getName());
				
				if(framePlayer != null)
				{
					framePlayer.cancel();
					ParticlePix.FramePlayers.remove(particleDisplay.getName());
				}
			}
			
			ParticlePix.ParticleDisplays.clear();
			
			BukkitRunnable asyncTask = new BukkitRunnable()
			{
				@Override
				public void run()
				{
					if(IOManager.saveDisplays())
					{
						MessageManager.success(sender, "Successfully deleted all displays");
					}
					else
					{
						MessageManager.error(sender, "An error occured when deleting the displays. Enable debug mode to see the error.");
					}
				}
			};
			asyncTask.runTaskAsynchronously(ParticlePix.plugin);
		}
		else
		{
			MessageManager.error(sender, "You do no have permission to use this command");
		}
	}

	@Override
	public ArgsLength getArgsLength()
	{
		ArgsLength argsLength = ArgsLength.EQUALS;
		argsLength.setNumArgs(0);
		
		return argsLength;
	}

	@Override
	public String commandUsage() {
		return ChatColor.GOLD + "/particlepix deletealldisplays" + ChatColor.GRAY + "- " + ChatColor.GREEN + 
				"Deletes all particle displays";
	}

}
