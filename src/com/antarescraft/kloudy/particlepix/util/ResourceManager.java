package com.antarescraft.kloudy.particlepix.util;

import java.util.Hashtable;

import org.bukkit.Bukkit;

import com.antarescraft.kloudy.particlepix.ParticleImage;
import com.antarescraft.kloudy.particlepix.ParticlePix;
import com.antarescraft.kloudy.particlepix.imageprocessing.ImageURL;
public class ResourceManager 
{
	public static Hashtable<String, ParticleImage> resourceParticleImages;
	
	public ResourceManager()
	{
		resourceParticleImages = new Hashtable<String, ParticleImage>();
	}
	
	public void loadResources()
	{
		String displayImageUrl = "http://www.pixelmobs.com/resources/images/ParticlePix.png";
		
		ImageURL imageUrl = new ImageURL(displayImageUrl, Bukkit.getConsoleSender(), "displayImage", resourceParticleImages);
		imageUrl.runTask(ParticlePix.plugin);
	}
	
	public Hashtable<String, ParticleImage> getResourceParticleImages()
	{
		return resourceParticleImages;
	}
}
