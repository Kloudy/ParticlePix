package com.antarescraft.kloudy.particlepix;

import java.io.Serializable;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public class ParticleDisplay implements Serializable
{
	private static final long serialVersionUID = 1L;
	private ParticleImage particleImage;
	private String displayName;
	private double x, y, z;
	private UUID worldUUID;
	private boolean isRunning;
	private DisplayDirection direction;
	
	public ParticleDisplay(String displayName, Location location)
	{
		this.displayName = displayName;
		x = location.getX();
		y = location.getY();
		z = location.getZ();
		worldUUID = location.getWorld().getUID();
		particleImage = null;
		isRunning = false;
		
		BlockFace dir = ParticlePix.yawToFace(location.getYaw());
		
		if(dir == BlockFace.NORTH)
		{
			direction = DisplayDirection.NORTH;
		}
		else if(dir == BlockFace.SOUTH)
		{
			direction = DisplayDirection.SOUTH;
		}
		else if(dir == BlockFace.EAST)
		{
			direction = DisplayDirection.EAST;
		}
		else if(dir == BlockFace.WEST)
		{
			direction = DisplayDirection.WEST;
		}
		
		startDisplayGif();
	}
	
	public void startDisplayGif()
	{
		particleImage = ParticlePix.ResourceManager.getResourceParticleImages().get("displayImage");
		isRunning = true;
		FramePlayer framePlayer = new FramePlayer(this);
		framePlayer.start();
	}
	
	public ParticleImage getParticleImage()
	{
		return particleImage;
	}
	
	public void setParticleImage(ParticleImage particleImage)
	{
		this.particleImage = particleImage;
	}
	
	public String getName()
	{
		return displayName;
	}
	
	public Location getLocation()
	{
		return new Location(Bukkit.getWorld(worldUUID), x, y, z);
	}
	
	public boolean getIsRunning()
	{
		return isRunning;
	}
	
	public void setIsRunning(boolean isRunning)
	{
		this.isRunning = isRunning;
	}
	
	public DisplayDirection getDirection()
	{
		return direction;
	}
}
