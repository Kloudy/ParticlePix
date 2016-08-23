package com.antarescraft.kloudy.particlepix.util;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigValues
{
	private final int DEFAULT_RENDER_DISTANCE = 25;
	private final int DEFAULT_MAX_WIDTH = 60;
	private final int DEFAULT_MAX_HEIGHT = 60;
	private final int DEFAULT_FRAMERATE = 3;
	private final boolean DEFAULT_DEBUG_MODE = false;
	private final int DEFAULT_PARTICLES_PER_FRAME = 1400;
	private final int DEFAULT_MAX_PING = 400;
	
	private int renderDistance;
	private int maxWidth;
	private int maxHeight;
	private int framerate;
	private boolean debugMode;
	private int particlesPerFrame;
	private int maxPing;
	
	public ConfigValues(FileConfiguration config)
	{
		renderDistance = DEFAULT_RENDER_DISTANCE;
		maxWidth = DEFAULT_MAX_WIDTH;
		maxHeight = DEFAULT_MAX_HEIGHT;
		framerate = DEFAULT_FRAMERATE;
		debugMode = DEFAULT_DEBUG_MODE;
		particlesPerFrame = DEFAULT_PARTICLES_PER_FRAME;
		maxPing = DEFAULT_MAX_PING;
		
		try
		{
			renderDistance = config.getInt("render-distance");
		}
		catch(Exception e){}
		try
		{
			maxWidth = config.getInt("image-max-width");
		}
		catch(Exception e){}
		try
		{
			maxHeight = config.getInt("image-max-height");
		}
		catch(Exception e){}
		try
		{
			framerate = config.getInt("framerate");
		}
		catch(Exception e){}
		try
		{
			debugMode = config.getBoolean("debug-mode");
		}
		catch(Exception e){}
		try
		{
			particlesPerFrame = config.getInt("particles-per-frame");
		}
		catch(Exception e){}
		try
		{
			maxPing = config.getInt("max-ping");
		}
		catch(Exception e){}
	}
	
	public int getRenderDistance()
	{
		return renderDistance;
	}
	
	public int getMaxWidth()
	{
		return maxWidth;
	}
	
	public int getMaxHeight()
	{
		return maxHeight;
	}
	
	public int getFramerate()
	{
		return framerate;
	}
	
	public boolean getDebugMode()
	{
		return debugMode;
	}
	
	public int getParticlesPerFrame()
	{
		return particlesPerFrame;
	}
	
	public int getMaxPing()
	{
		return maxPing;
	}
}