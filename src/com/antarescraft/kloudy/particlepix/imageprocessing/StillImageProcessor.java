package com.antarescraft.kloudy.particlepix.imageprocessing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.bukkit.command.CommandSender;

import com.antarescraft.kloudy.particlepix.ParticleImage;
import com.antarescraft.kloudy.particlepix.ParticlePix;
import com.antarescraft.kloudy.particlepix.util.IOManager;
import com.antarescraft.kloudy.particlepix.util.MessageManager;

public class StillImageProcessor
{
	public static void processImage(CommandSender sender, String imageName, ImageType imageType,
			File imageFile, Hashtable<String, ParticleImage> imageCollection)
	{
		try
		{
			BufferedImage image = ImageIO.read(imageFile);
			
			int[][][] colors = new int[1][][];
			
			int numPixels = BaseImageProcessor.getPixelCount(image);
			colors[0] = BaseImageProcessor.processFrame(image, numPixels);
			
			ParticleImage particleImage = new ParticleImage(colors, imageName);
			imageCollection.put(imageName, particleImage);
			
			IOManager.saveImage(sender, particleImage);
			
			MessageManager.success(sender, "Image successfully processed!");
		}
		catch(Exception e)
		{
			if(ParticlePix.configValues.getDebugMode())
			{
				e.printStackTrace();
			}
			
			BaseImageProcessor.processingError(sender);
		}
	}
}
