package com.antarescraft.kloudy.particlepix.imageprocessing;

import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FileInputStream;


//import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;


import com.antarescraft.kloudy.particlepix.ParticlePix;
/*import com.antarescraft.kloudy.particlepix.ParticlePix;
import com.antarescraft.kloudy.particlepix.ParticleImage;
import com.antarescraft.kloudy.particlepix.util.IOManager;*/
import com.antarescraft.kloudy.particlepix.util.MessageManager;

public class BaseImageProcessor 
{	
	//returns the number of non-transparent pixels
	public static int getPixelCount(BufferedImage image)
	{
		int pixelCount = 0;

		for(int y = 0; y < image.getHeight(); y++)
		{
			for(int x = 0; x < image.getWidth(); x++)
			{
				int rgb = image.getRGB(x, y);
				int mask = 0xFF;
				int alpha = (rgb >> 24) & mask;
				
				if(alpha != 0)
				{
					pixelCount += 1;
				}
			}
		}
		
		return pixelCount;
	}
	
	//returns 2D array of integer colors
	public static int[][] processFrame(BufferedImage image, int numPixels)
	{
		double particleRatio = Math.sqrt(ParticlePix.configValues.getParticlesPerFrame()*1.0 / numPixels);
		int width = (int) (image.getWidth() * particleRatio);
		int height = (int) (image.getHeight() * particleRatio);
		int type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
		image = GifDecoder.resizeImageWithHint(image, type, width, height);
		
		int[][] colors = new int[height][width];
		
		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{
				int rgb = image.getRGB(x, y);
				colors[y][x] = rgb;
			}
		}
		
		return colors;
	}
	
	public static void processingError(CommandSender sender)
	{
		MessageManager.error(sender, "Non-direct URL or unsupported filetype. Try uploading the image to imgur.com and using the direct link to the image from there.\n");
	}
}
