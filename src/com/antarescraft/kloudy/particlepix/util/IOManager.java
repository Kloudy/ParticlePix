package com.antarescraft.kloudy.particlepix.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.antarescraft.kloudy.particlepix.ParticleDisplay;
import com.antarescraft.kloudy.particlepix.ParticleImage;
import com.antarescraft.kloudy.particlepix.ParticlePix;

public class IOManager
{
	public static final String PATH_TO_ROOT = "plugins/ParticlePix";
	public static final String PATH_TO_IMAGES = PATH_TO_ROOT + "/imagedata";
	public static final String PATH_TO_DISPLAYS = PATH_TO_ROOT + "/displaydata";
	public static final String PATH_TO_RESOURCES = "/com/antarescraft/kloudy/particlepix/resources";
	
	public static void initFileStructure()
	{
		try
		{
			File folder = new File(PATH_TO_ROOT);
			if(!folder.exists())
			{
				folder.mkdir();
			}
			
			folder = new File(PATH_TO_DISPLAYS);
			if(!folder.exists())
			{
				folder.mkdir();
			}
			
			folder = new File(PATH_TO_IMAGES);
			if(!folder.exists())
			{
				folder.mkdir();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static ArrayList<String> getImageNames()
	{
		ArrayList<String> names = new ArrayList<String>();
		File folder = new File(IOManager.PATH_TO_IMAGES);
		File[] files = null;
		
		if(folder.exists())
		{
			files = folder.listFiles();
			
			for(File file : files)
			{
				if(file.isFile() && file.getName().endsWith(".dat"))
				{
					names.add(file.getName().replaceAll(".dat", ""));
				}
			}
		}
		
		Collections.sort(names, new Comparator<String>() {
	        @Override
	        public int compare(String s1, String s2) {
	            return s1.compareToIgnoreCase(s2);
	        }
	    });
		
		return names;
	}
	
	public static ParticleImage loadImage(CommandSender sender, String imageName)
	{
		ParticleImage particleImage = null;
		File imageFile = new File(PATH_TO_IMAGES + "/" + imageName + ".dat");
		
		try
		{
			FileInputStream fileIn = new FileInputStream(imageFile);
			ObjectInputStream in = null;
			
			if(imageFile.length() > 0)
			{				
				in = new ObjectInputStream(fileIn);
				particleImage = (ParticleImage) in.readObject();
				in.close();
			}
			MessageManager.success(sender, "Image loaded sucessfully");
		}
		catch(Exception e)
		{
			if(ParticlePix.configValues.getDebugMode())
			{
				e.printStackTrace();
			}
			
			MessageManager.error(sender, "An error was encountered when trying to load that image");
		}
		
		return particleImage;
	}
	
	public static void saveImage(CommandSender sender, ParticleImage particleImage)
	{
		try
		{
			File image = new File(IOManager.PATH_TO_IMAGES+ "/" + particleImage.getName() + ".dat");
			
			if(!image.exists())
			{
				image.createNewFile();
			}
			
			FileOutputStream fileOut = new FileOutputStream(image);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			
			out.writeObject(particleImage);
			out.close();
			
			String message = ChatColor.GOLD + "Image ";
			message += ChatColor.AQUA + "'" + particleImage.getName() + "'";
			message += ChatColor.GOLD + " was successfully processed!";
			
			sender.sendMessage(message);
		}
		catch(IOException e)
		{
			if(ParticlePix.configValues.getDebugMode())
			{
				MessageManager.error(sender, "An error occured saving the image");
				e.printStackTrace();
			}
			else
			{
				MessageManager.error(sender, "An error occured saving the image. Enable debug mode in the config file to see the error.");
			}
		}
	}
	
	public static void deleteImage(CommandSender sender, String imageName)
	{
		File file = new File(PATH_TO_IMAGES  + "/" + imageName + ".dat");
		
		if(file.exists() && file.isFile())
		{
			file.delete();
			
			MessageManager.success(sender, "Image was successfully deleted");
		}	
		else
		{
			MessageManager.error(sender, "Could not find that image");
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void loadDisplays()
	{
		File particleDisplaysFile = new File(PATH_TO_DISPLAYS + "/displaydata.dat");
		
		try
		{
			FileInputStream fileIn = new FileInputStream(particleDisplaysFile);
			ObjectInputStream in = null;
			
			if(particleDisplaysFile.length() > 0)
			{				
				in = new ObjectInputStream(fileIn);
				ParticlePix.ParticleDisplays = (Hashtable<String, ParticleDisplay>) in.readObject();
				in.close();
			}
		}
		catch(Exception e)
		{
			if(ParticlePix.configValues.getDebugMode())
			{
				e.printStackTrace();
			}
		}
	}
	
	public static boolean saveDisplays()
	{
		boolean success = false;
		try
		{
			File particleDisplaysFile = new File(PATH_TO_DISPLAYS + "/displaydata.dat");
			
			if(!particleDisplaysFile.exists())
			{
				particleDisplaysFile.createNewFile();
			}
			
			FileOutputStream fileOut = new FileOutputStream(particleDisplaysFile);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			
			out.writeObject(ParticlePix.ParticleDisplays);
			out.close();
			
			success = true;
		}
		catch(IOException e)
		{
			if(ParticlePix.configValues.getDebugMode())
			{
				e.printStackTrace();
			}
		}
		
		return success;
	}
	
	public static void saveToggledPlayers()
	{
		try
		{
			File togglePlayers = new File(PATH_TO_ROOT + "/toggledPlayers.dat");
			
			if(!togglePlayers.exists())
			{
				togglePlayers.createNewFile();
			}
			
			FileOutputStream fileOut = new FileOutputStream(togglePlayers);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			
			out.writeObject(ParticlePix.PlayerNoShow);
			out.close();
		}
		catch(IOException e)
		{
			if(ParticlePix.configValues.getDebugMode())
			{
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void loadToggledPlayers()
	{
		File toggledPlayers = new File(PATH_TO_ROOT + "/toggledPlayers.dat");
		
		try
		{
			FileInputStream fileIn = new FileInputStream(toggledPlayers);
			ObjectInputStream in = null;
			
			if(toggledPlayers.length() > 0)
			{				
				in = new ObjectInputStream(fileIn);
				ParticlePix.PlayerNoShow = (Hashtable<UUID, String>) in.readObject();
				in.close();
			}
		}
		catch(Exception e)
		{
			if(ParticlePix.configValues.getDebugMode())
			{
				e.printStackTrace();
			}
		}
	}
}
