package com.antarescraft.kloudy.particlepix.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import com.antarescraft.kloudy.particlepix.ParticlePix;
import com.antarescraft.kloudy.particlepix.util.IOManager;
import com.antarescraft.kloudy.particlepix.util.MessageManager;

public class DeleteimageCommand implements Callable
{
	
	@Override
	public void call(final CommandSender sender, final ArrayList<String> args)
	{
		if(sender.hasPermission("pp.image"))
		{
			BukkitRunnable asyncTask = new BukkitRunnable()
			{
				@Override
				public void run()
				{
					IOManager.deleteImage(sender, args.get(0));
				}
			};
			
			asyncTask.runTaskAsynchronously(ParticlePix.plugin);
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
		return ChatColor.GOLD + "/particlepix deleteimage <image_name>" + ChatColor.GRAY + "- " + ChatColor.GREEN + 
				"Deletes the specified imag if it exists";
	}

}
