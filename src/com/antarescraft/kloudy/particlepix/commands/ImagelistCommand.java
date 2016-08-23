package com.antarescraft.kloudy.particlepix.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import com.antarescraft.kloudy.particlepix.ParticlePix;
import com.antarescraft.kloudy.particlepix.util.IOManager;
import com.antarescraft.kloudy.particlepix.util.MessageManager;

public class ImagelistCommand implements Callable
{
	//particlepix imagelist [page]
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
					ArrayList<String> imageNames = IOManager.getImageNames();
					
					if(args.size() > 0)
					{
						MessageManager.pageList(sender, imageNames, args.get(0), "Images");
					}
					else
					{
						MessageManager.pageList(sender, imageNames, "Images");
					}
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
	public ArgsLength getArgsLength() {
		ArgsLength argsLength = ArgsLength.GREATER_THAN_OR_EQUAL_TO;
		argsLength.setNumArgs(0);
		
		return argsLength;
	}

	@Override
	public String commandUsage() {
		return ChatColor.GOLD + "/particlepix imagelist [page]" + ChatColor.GRAY + "- " + ChatColor.GREEN + 
				"Shows a list of all the created images";
	}
}
