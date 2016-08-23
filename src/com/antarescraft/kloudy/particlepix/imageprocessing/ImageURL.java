package com.antarescraft.kloudy.particlepix.imageprocessing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;

import org.apache.commons.io.IOUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import com.antarescraft.kloudy.particlepix.ParticleImage;
import com.antarescraft.kloudy.particlepix.util.IOManager;

public class ImageURL extends BukkitRunnable
{
	private String webURL;
	private CommandSender sender;
	private String imageName;
	private Hashtable<String, ParticleImage> imageCollection;
	
	public ImageURL(String webURL, CommandSender player, String imageName, Hashtable<String, ParticleImage> imageCollection)
	{
		this.webURL = webURL;
		this.sender = player;
		this.imageName = imageName;
		this.imageCollection = imageCollection;
	}
	
	@Override
	public void run()
	{
		URL url = null;
		
		try 
		{
			url = new URL(webURL);
			URLConnection connection = url.openConnection();
			connection.setReadTimeout(20000);
			InputStream is = connection.getInputStream();
			
			String filePath = IOManager.PATH_TO_IMAGES + "/" + imageName;
			
			ImageType contentType = null;
			
			if(connection.getContentType().contains("gif"))
			{
				filePath += ".gif";
				contentType = ImageType.GIF;
			}
			else if(connection.getContentType().contains("jpeg"))
			{
				filePath += ".jpg";
				contentType = ImageType.JPEG;
			}
			else if(connection.getContentType().contains("png"))
			{
				filePath += ".png";
				contentType = ImageType.PNG;
			}
			else
			{
				throw new Exception();
			}
			
			File imageFile = new File(filePath);
			
			if(imageFile.exists())
			{
				imageFile.delete();
			}
			
			imageFile.createNewFile();
			
			FileOutputStream output = new FileOutputStream(imageFile);
			
			output.write(IOUtils.toByteArray(is));
			is.close();
			output.close();
			
			if(contentType == ImageType.GIF)
			{
				AnimatedImageProcessor.processImage(sender, imageName, contentType, imageFile, imageCollection);
			}
			else if(contentType == ImageType.JPEG || contentType == ImageType.PNG)
			{
				StillImageProcessor.processImage(sender, imageName, contentType, imageFile, imageCollection);
			}
		}
		catch(Exception e)
		{
			BaseImageProcessor.processingError(sender);
 		}
	}
}
