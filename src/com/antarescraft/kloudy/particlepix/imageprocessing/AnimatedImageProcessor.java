package com.antarescraft.kloudy.particlepix.imageprocessing;

import java.io.File;
import java.io.FileInputStream;
import java.util.Hashtable;

import org.bukkit.command.CommandSender;

import com.antarescraft.kloudy.particlepix.ParticleImage;
import com.antarescraft.kloudy.particlepix.ParticlePix;
import com.antarescraft.kloudy.particlepix.util.IOManager;
import com.antarescraft.kloudy.particlepix.util.MessageManager;

public class AnimatedImageProcessor
{
	public static void processImage(CommandSender sender, String imageName, ImageType imageType, 
			File imageFile, Hashtable<String, ParticleImage> imageCollection)
	{
		try
		{
			FileInputStream fileInput = new FileInputStream(imageFile);
			
			GifDecoder decoder = new GifDecoder();
			int code = decoder.read(fileInput);
			
			if(code == 0)
			{	
				int frameCount = decoder.getFrameCount();
				
				int[][][] colors = new int[frameCount][][];
				
				int highestPixelCount = 0;
				
				//preprocess the image to get pixelcount
				for(int i = 0; i < frameCount; i++)
				{
					int pixelCount = BaseImageProcessor.getPixelCount(decoder.getFrame(i));
					
					if(pixelCount > highestPixelCount)
					{
						highestPixelCount = pixelCount;
					}
				}
				
				//get colors
				for(int i = 0; i < frameCount; i++)
				{
					colors[i] = BaseImageProcessor.processFrame(decoder.getFrame(i), highestPixelCount);
				}
				
				ParticleImage particleImage = new ParticleImage(colors, imageName);
				imageCollection.put(imageName, particleImage);
				
				IOManager.saveImage(sender, particleImage);
				
				MessageManager.success(sender, "Image successfully processed!");
				
				fileInput.close();
			}
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
