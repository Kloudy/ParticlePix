package com.antarescraft.kloudy.particlepix.imageprocessing;

import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import com.antarescraft.kloudy.particlepix.FramePlayer;
import com.antarescraft.kloudy.particlepix.ParticleDisplay;
import com.antarescraft.kloudy.particlepix.ParticleImage;
import com.antarescraft.kloudy.particlepix.util.IOManager;
import com.antarescraft.kloudy.particlepix.util.MessageManager;

public class ImageLauncher extends BukkitRunnable
{
	private static CommandSender sender;
	private ParticleDisplay particleDisplay;
	private String imageName;
	
	public ImageLauncher(CommandSender sender, ParticleDisplay particleDisplay, String imageName)
	{
		ImageLauncher.sender = sender;
		this.particleDisplay = particleDisplay;
		this.imageName = imageName;
	}

	@Override
	public void run() 
	{
		ParticleImage particleImage = IOManager.loadImage(sender, imageName);

		if(particleImage != null)
		{
			particleDisplay.setParticleImage(particleImage);
			particleDisplay.setIsRunning(true);

			start(particleDisplay);
		}
	}
	
	public static void start(ParticleDisplay particleDisplay)
	{	
			FramePlayer framePlayer = new FramePlayer(particleDisplay);
			framePlayer.start();
			
			MessageManager.success(sender, "Image started!");
	}
}
